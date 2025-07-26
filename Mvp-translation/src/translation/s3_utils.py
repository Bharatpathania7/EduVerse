import boto3
import tempfile
import os

s3 = boto3.client("s3")

def download_from_s3(s3_url):
    s3_url = s3_url.replace("https://", "")
    parts = s3_url.split("/", 1)
    bucket = parts[0].split(".")[0]
    key = parts[1]

    temp_file = tempfile.NamedTemporaryFile(delete=False, suffix=".mp4")
    s3.download_file(bucket, key, temp_file.name)
    return temp_file.name

def upload_to_s3(file_path, s3_key):
    bucket = "your-bucket"  # Replace with your actual bucket
    s3.upload_file(file_path, bucket, s3_key)
    print(f"✅ Uploaded to s3://{bucket}/{s3_key}")
