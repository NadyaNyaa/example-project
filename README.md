#Appointment Management Example Project

### before running the application

The following docker container has be running:

`docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root --name mysql mysql:5.7`

Then, a schema named **"appointment-management"** has to be created.

### some explanations

I added an admin user that is the only one who can manage users. Only users added by this admin user can manage events (and only edit/delete the ones they created).

Due to the time constraint, I wasn't able to write proper unit tests. There is a test-requests.http file in the src folder to test out the basic functionality.
    
In the current implementation, one event always belongs to one user. If required, this could be expanded to that multiple people can have the same event.
