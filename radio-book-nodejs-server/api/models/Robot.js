var mongoose = require('mongoose')

// Define schema for Robot

//d = doc._id.getTimestamp(); print(d.getFullYear()+"-"+(d.getMonth()+1)+
//"-"+d.getDate() + " " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds()

var robot = new mongoose.Schema({
  _id: {
    type: String,
    required: true
  },
  organization_id: {
    type: String,
    required: true
  },
  robot_name: {
    type: String,
    required: true
  },
  battery_level: {
    type: String,
    required: true
  },
  robot_status: {
    type: String,
    required: true
  },
  // robot_uptime: {
  //   type: String,
  //   required: true
  // }
},
{
  collection: 'robots'
}
)

module.exports = mongoose.model('Robot', robot)