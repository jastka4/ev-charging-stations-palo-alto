# EV charging stations in Palo Alto

1. Install Docker and run it

2. Clone the repo and go to the project directory

   ```
   $ git clone https://github.com/jastka4/ev-charging-stations-palo-alto.git
   $ cd ev-charging-stations-palo-alto
   ```

3. Run Docker Compose:
    ```
    $ docker-compose up
    ```

3. Wait a second for Docker to finish setting up all the containers.

4. Navigate to [http://localhost:8082/swagger-ui/](http://localhost:8082/swagger-ui/) to see all available endpoints.
   You can also use a Postman collection that you can preview [here](endpoints.postman_collection.json).

---

### Would you like to run the apps separately?

- Check [this readme](python-api/README.md) out to run the Python API.

- Check [this readme](java-api/README.md) out to run the Java API.
