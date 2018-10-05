'use strict'

var express = require('express')
let Client = require('ssh2-sftp-client');
var node_ssh = require('node-ssh')
var ssh = new node_ssh()

var username = 'e14262'
var pass = '95+@-san'
var server = 'aiken.ce.pdn.ac.lk'

var sftpRoutes = express.Router()

sftpRoutes.route('/getDirList').post(function(req, res){
    
       var path = req.body.path;
    
       let sftp = new Client();
       sftp.connect({
           host:  server,
           port: '22',
           username: username,
           password: pass
       }).then(() => {
           return sftp.list(path);
       }).then((data) => {
           res.send(data)
           sftp.end();
           console.log('SFTP Connection Closed');
       }).catch((err) => {
           console.log(err, 'catch error');
           res.send('SFTP Error')
       });
    
})

sftpRoutes.route('/sftp').post(function(req, res){

   var filename = req.body.filename;
   var uploadPath = req.body.uploadpath;

   let sftp = new Client();
   sftp.connect({
       host:  server,
       port: '22',
       username: username,
       password: pass
   }).then(() => {
       ///return sftp.list(uploadPath);
       return sftp.put(filename, uploadPath, false);
   }).then(() => {
       console.log('Upload Succees');
       res.send('Upload Succees')
       sftp.end();
       console.log('SFTP Connection Closed');
   }).catch((err) => {
       console.log(err, 'catch error');
       res.send('Upload Error')
   });

})

sftpRoutes.route('/runjar').post(function(req, res){
    
    var command = req.body.command;
    var uploadPath = req.body.uploadpath;
    
    ssh.connect({
        host: server,
        username: username,
        password: pass
    }).then(function(){
        console.log('SSH Ready')
        // Command
        ssh.execCommand(command, { cwd: uploadPath }).then(function(result) {
            console.log('STDOUT: ' + result.stdout)
            console.log('STDERR: ' + result.stderr)
        })
    })
    
})

module.exports = sftpRoutes