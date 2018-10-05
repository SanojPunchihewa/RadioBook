var mongoose = require('mongoose')

// Define schema for Organization

var organization = new mongoose.Schema({
  organization_name: {
    type: String,
    required: true
  },
  organization_owner_id: {
    type: String,
    required: true
  }
},
{
  collection: 'organization'
}
)

module.exports = mongoose.model('Organization', organization)