from app import database, marshmallow
from .station import StationSchema


class Port(database.Model):
    __tablename__ = 'port'

    port_type = database.Column(
        database.String(7),
        nullable=False
    )
    port_number = database.Column(
        database.SmallInteger,
        primary_key=True
    )
    plug_type = database.Column(
        database.String(10),
        nullable=False
    )
    station_name = database.Column(
        database.String(30),
        database.ForeignKey('station.station_name'),
        primary_key=True,
        index=True
    )
    station = database.relationship('Station', backref='ports')

    def __repr__(self):
        return '<Port {}, {}>'.format(self.port_number, self.station_name)


class PortSchema(marshmallow.SQLAlchemyAutoSchema):
    class Meta:
        model = Port
        load_instance = True

    station = marshmallow.Nested(StationSchema)


port_schema = PortSchema()
ports_schema = PortSchema(many=True)
