# IT_Project
# Set up
Install libraries: <code>pip install -r requirements.txt</code>.

Go to: **Project settings > Service accounts > Generate new private key.** </br>
https://console.firebase.google.com/u/0/project/[PROJECT-NAME]/settings/serviceaccounts/adminsdk </br>
Rename *.json to *key.json* and save in folder *backend*.

Also, add "Web app" in **Project settings > General**.</br>
Save firebaseConfig as *config.json* in folder *backend*.</br>

In config.py, replace your compatible keys.


# Run app
On cmd: <code> py app.py </code> <br>
Access: http://127.0.0.1:5000/



# API Endpoints
| Resource class      | URI                                        | Description                                               | Method           | Input                                                                                                                   | Output                                                                                                                                                                                                                                                                                 |
|---------------------|--------------------------------------------|-----------------------------------------------------------|------------------|-------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| UserResource        | `/api/users`                               | User registration and authentication                      | POST             | JSON: `{ "email": "user@example.com", "password": "user_password" }`                                                    | JSON: `{ "message": "User registered successfully", "user_id": "123456" }`                                                                                                                                                                                                             |
| ModelResource       | `/api/models`                              | Create a new model for the user                           | GET,POST, DELETE | JSON: `{ "user_id": "123456", "model_id": "789012", "model_name": "apple_fruit" }`                                      | JSON: `{ "message": "Model created, images are being crawled asynchronously" }`                                                                                                                                                                                                        |
| ModelDetailResource | `/api/models/{model_id}`                   | Retrieve details of a specific model                      | GET, DELETE      |                                                                                                                         | JSON: `{ "model_data": { "model_id": "789012", "user_id": "123456", "model_name": "apple_fruit", "images": [{ "image_id": "image_1", "url": "https://example.com/image_1.jpg", "roi_values": [1, 2] }] } }`                                                                            |
| ImageResource       | `/api/images`                              | List images of a model                                    | GET              | JSON: `{ "model_id": "789012", "image_id": "image_1", "url": "https://example.com/image_1.jpg", "roi_values": [1, 2] }` | JSON: `{ "message": "Image created successfully" }`                                                                                                                                                                                                                                    |
| ImageDetailResource | `/api/models/{model_id}/images/{image_id}` | Retrieve details or update ROI values of a specific image | GET, PUT, DELETE | GET: None PUT: JSON: `{ "roi_values": [3, 4] }`                                                                         | GET: `{ "image_data": { "image_id": "image_1", "url": "https://example.com/image_1.jpg", "roi_values": [1, 2] } }` PUT: `{ "message": "Image updated successfully", "updated_image_data": { "image_id": "image_1", "url": "https://example.com/image_1.jpg", "roi_values": [3, 4] } }` |

