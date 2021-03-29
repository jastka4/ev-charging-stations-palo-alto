from app import database, marshmallow
from .address import AddressSchema


class Station(database.Model):
    __tablename__ = 'station'

    station_name = database.Column(
        database.String(30),
        primary_key=True
    )
    mac_address = database.Column(
        database.String(19),
        nullable=False
    )
    org_name = database.Column(
        database.String(20),
        nullable=False
    )
    latitude = database.Column(
        database.Float,
        nullable=False
    )
    longitude = database.Column(
        database.Float,
        nullable=False
    )
    address_name = database.Column(
        database.String(30),
        database.ForeignKey('address.address'),
        nullable=False
    )
    address = database.relationship('Address', backref='stations')

    def __repr__(self):
        return '<Station {}>'.format(self.station_name)


class StationSchema(marshmallow.SQLAlchemyAutoSchema):
    class Meta:
        model = Station
        load_instance = True

    address = marshmallow.Nested(AddressSchema)


station_schema = StationSchema()
stations_schema = StationSchema(many=True)
