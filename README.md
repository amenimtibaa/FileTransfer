## Client/Server File exchange 

This is a simple client-server application for uploading/downloading files. 

The project is divided as follows: 

*  A client project folder: A client can select a file, encrypt it, and upload it to the server. He can also download and decrypt the previously uploaded file from the server.
*  A server project folder: The server can execute multiple client requests. The server receives files from clients and serves the files back to them upon a request.
*  A Library project folder: This project contains the source code for the JAR files of encryption/decryption and sending/receiving files. The goal is to be able to use those libs in any project without recoding.


### Quick Start

#### Dependencies

Each project folder is packed with its own dependencies. The path to the JAR is already configured in the `.classpath` file of each project folder.

In case of an error, please verify the Classpath in the: 'Build Path > configure build path > Libraries' tab in your IDE.

#### Installing and Executing

These instructions will get you a copy of the project up and running:

* fist install the project
```
git clone https://github.com/amenimtibaa/FileTransfer.git
cd FileTransfer
```
* Then compile and run the server
```
cd server/ src
javac -cp ".:./lib/custom_libs.jar" Server.java
java -cp ".:./lib/custom_libs.jar" Server
```
* Same with the client
```
cd client/src
javac -cp ".:./lib/custom_libs.jar:.:./lib/bcprov-jdk15on-159.jar" testClientSever.java 
java -cp ".:./lib/custom_libs.jar:.:./lib/bcprov-jdk15on-159.jar" testClientSever
 
```
### TO DO
This is a very basic client-server exchange app, it can be improved as follow: 

- [ ] Upgrade to an SSL Socket connection for secure data exchange.

- [ ] Upgrade client and add certificate to verify files integrity.

- [ ] Automate some stuff like file name, keystore password and path.

- [ ] Handle exceptions more properly.

