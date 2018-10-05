var mongoose = require('mongoose')

// Define schema for Notification

var notification = new mongoose.Schema({
  user_id: {
    type: String,
    required: true
  },
  organization_id: {
    type: String,
    required: true
  },
  notification_type: {
    type: String,
    required: true
  },
  notification: {
    type: String,
    required: true
  },
  notification_meta: {
    type: String
  },
  is_read: {
    type: String,
    required:true
  }
},
{
  collection: 'notifications'
}
)

module.exports = mongoose.model('Notification', notification)