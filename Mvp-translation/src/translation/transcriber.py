import whisper
import os
from src.translation.s3_utils import download_from_s3, upload_to_s3
import whisper.utils

model = whisper.load_model("base")

def process_video(s3_url):
    video_path = download_from_s3(s3_url)
    result = model.transcribe(video_path, language="en")

    srt_path = video_path.replace(".mp4", ".srt")
    whisper.utils.write_srt(result["segments"], srt_path)

    # Upload to S3 (example path structure)
    s3_key = "subtitles/" + os.path.basename(srt_path)
    upload_to_s3(srt_path, s3_key)

    return f"https://your-bucket.s3.ap-south-1.amazonaws.com/{s3_key}"

