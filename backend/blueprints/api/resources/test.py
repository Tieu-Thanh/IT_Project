import sys
import io
import os
from types import SimpleNamespace


# result = model.detect(r"D:\acc\IT_Project\backend\video1710136324728.mp4")

def create_video_file_mock( video_path):
    # Mở file và đọc nội dung
    print(video_path)
    with open(video_path, 'rb') as file:
        file_content = file.read()

    # Tạo một đối tượng BytesIO (stream) từ nội dung file
    video_stream = io.BytesIO(file_content)
    print(f"video_stream{video_stream}")
    video_file_mock = SimpleNamespace(
        stream=video_stream,
        filename=os.path.basename(video_path),
        content_type='video/avi'
    )

    return video_file_mock
video = create_video_file_mock(r"D:\acc\IT_Project\backend\blueprints\api\resources\Images\aRquBCV0DrVkPTfRYv57Gh9vqO23\CO3_model\detection_result\08f1b453-467b-4b25-bf3f-db57dcb27cdb\video1710143565016.avi")
print(video.filename)
print(video.stream)