var mongoose = require('mongoose')

// Define schema for Last Used of Robots

var lastUsed = new mongoose.Schema({
  bot_id: {
    type: String,
    required: true
  },
  user_id: {
    type: String,
    required: true
  },
  user_name: {
    type: String,
    required: true
  }
},
{
  collection: 'lastUsed'
}
)

module.exports = mongoose.model('LastUsed', lastUsed)