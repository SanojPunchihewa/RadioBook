var mongoose = require('mongoose')

// Define schema for User

var user = new mongoose.Schema({
  first_name: {
    type: String,
    required: true
  },
  last_name: {
    type: String,
    required: true
  },
  office_phone: {
    type: String
  },
  mobile_phone: {
    type: String
  },
  email: {
    type: String,
    required: true,
    unique: true
  },
  password: {
    type: String,
    required: true
  },
  display_name: {
    type: String,
    required: true,
    unique: true
  },
  account_type: {
    type: String,
    required: true
  },
  account_status: {
    type: String,
    required: true
  },
  organization_id: {
    type: String,
    required: true
  }
},
{
  collection: 'users'
}
)

module.exports = mongoose.model('User', user)