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
