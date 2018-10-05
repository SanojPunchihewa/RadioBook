var mongoose = require('mongoose')

// Define schema for Logs

var robotLogs = new mongoose.Schema({
  bot_id: {
    type: String,
    required: true
  },
  log_message: {
    type: String,
    required: true
  },
  log_data: {
    type: String,
    required: true
  }
},
{
  collection: 'robotLogs'
}
)

module.exports = mongoose.model('RobotLogs', robotLogs)