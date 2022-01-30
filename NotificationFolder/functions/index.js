'use strict'

const functions = require("firebase-functions");
const admin = require("firebase-admin");
admin.initializeApp(functions.config().firebase);

exports.sendNotification = functions.database.ref('/notifications/{receiver_user_id}/{notification_id}')
    .onWrite((data, context) => {
        const receiver_user_id = context.params.receiver_user_id;
        const notification_id = context.params.notification_id;

        console.log("we have a notification: ", receiver_user_id);

        if(!data.after.val()){
            console.log("Deleted: ", notification_id);
            return null;
        }

        const sender_user_id = admin.database().ref(`/notifications/${receiver_user_id}/${notification_id}`).once('value');
       
        return sender_user_id.then(fromUserResult => {
            const from_sender_user_id = fromUserResult.val().from;
            console.log("you have a notification from: ", sender_user_id);
            const userQuery = admin.database().ref(`/users/${receiver_user_id}/device_token:`).once('value');
            
            return userQuery.then(userResult => {
                const senderUserName = userResult.val();
                const deviceToken = admin.database().ref(`/users/${receiver_user_id}/device_token`).once('value');
        
                return deviceToken.then(result => {
                    const token_id = result.val();
                    const payload = {
                        notification: {
                            from_sender_user_id: from_sender_user_id,
                            title: "New notification",
                            body: `${senderUserName} wants connect for you`,
                            icon: "default"
                        }
                    };
                    return admin.messaging().sendToDevice(token_id, payload).then(response => {
                        console.log("this notification feature")
                    });
                });
            });
        });
    });

// C:\_OJO_NEW_ACCOUNT_ANDROID_DEVELOPER\islove.app\NotificationFolder>firebase deploy
// dont work !!! need to pay