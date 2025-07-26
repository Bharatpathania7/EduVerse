from flask import Flask, request, jsonify
from transcriber import process_video

app = Flask(__name__)

@app.route("/process", methods=["POST"])
def process():
    data = request.get_json()
    s3_url = data.get("s3Url")

    if not s3_url:
        return jsonify({"error": "Missing s3Url"}), 400

    try:
        result = process_video(s3_url)
        return jsonify({"message": "Transcription done", "srtFile": result})
    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == "__main__":
    app.run(port=5000)
