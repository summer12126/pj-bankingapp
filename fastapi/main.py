from fastapi import FastAPI
from elasticsearch import Elasticsearch
import mlflow
import os

app = FastAPI()

# Elasticsearch 연결
es = Elasticsearch([f"http://{os.getenv('ELASTICSEARCH_HOST')}:{os.getenv('ELASTICSEARCH_PORT')}"])

# MLflow 설정
mlflow.set_tracking_uri(os.getenv('MLFLOW_TRACKING_URI'))

@app.get("/health")
async def health_check():
    return {"status": "healthy"}

@app.get("/")
async def root():
    return {"message": "FastAPI is running"}

# ML 모델 예측 엔드포인트
@app.post("/predict")
async def predict(data: dict):
    try:
        # MLflow에서 모델 로드
        model = mlflow.sklearn.load_model("models:/my_model/Production")
        prediction = model.predict([data['features']])
        
        # 결과 저장
        es.index(index="predictions", document={
            "input": data,
            "prediction": prediction.tolist(),
            "timestamp": datetime.now()
        })
        
        return {"prediction": prediction.tolist()}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))