from flask import Blueprint, request, jsonify
from firebase_admin import auth, firestore

auth_bp = Blueprint("auth", __name__)


@auth_bp.route('/signup', methods=['POST'])
def signup():
    try:
        data = request.get_json()
        email = data.get('email')
        password = data.get('password')

        # Create a new user
        user = auth.create_user(email=email, password=password)

        # Additional user information to store in Firestore
        user_data = {
            'uid': user.uid,
            'email': user.email,
            'display_name': None,  # Add any additional fields you want
            'phone_number': None,
            'photo_url': None
        }

        # Create a new document in the 'users' collection in Firestore
        firestore.client().collection('users').document(user.uid).set(user_data)

        # Send email verification
        verification_link = auth.generate_email_verification_link(user.email)

        return jsonify({
            "message": "User created successfully. Verification email sent.",
            "user_id": user.uid,
            "verification_link": verification_link
        }), 201
    except Exception as e:
        return jsonify({"error": str(e)}), 400


@auth_bp.route('/login', methods=['POST'])
def login():
    try:
        data = request.get_json()
        id_token = data.get('id_token')

        # Verify the ID token
        decoded_token = auth.verify_id_token(id_token)

        # Get the user ID from the decoded token
        user_id = decoded_token['uid']

        return jsonify({
            "message": "Login successful",
            "user_id": user_id
        }), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 401


@auth_bp.route('/forgot-password', methods=['POST'])
def forgot_password():
    try:
        data = request.get_json()
        email = data.get('email')

        # Send password reset email
        auth.generate_password_reset_link(email)

        return jsonify({"message": "Password reset email sent."}), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 400


# @auth_bp.route('/revert-email-change', methods=['POST'])
# def revert_email_change():
#     try:
#         data = request.get_json()
#         old_email = data.get('old_email')
#
#         # Send email revert verification
#         auth.generate_email_revert_link(old_email)
#
#         return jsonify({"message": "Email revert verification email sent."}), 200
#     except Exception as e:
#         return jsonify({"error": str(e)}), 400
