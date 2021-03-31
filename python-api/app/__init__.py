from flask import Flask
from flask_marshmallow import Marshmallow
from flask_sqlalchemy import SQLAlchemy

from db import process_csv

app = Flask(__name__)
app.config.from_object('config.Config')
database = SQLAlchemy(app)
marshmallow = Marshmallow(app)

if app.config['INIT_DB_LOAD'] == 'True' and process_csv.should_init(database):
    process_csv.init_db_data(database)

from app import routes
