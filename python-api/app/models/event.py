from app import database, marshmallow
from .port import PortSchema


class Event(database.Model):
    __tablename__ = 'event'

    plug_in_event_id = database.Column(
        database.BigInteger,
        primary_key=True
    )
    start_date = database.Column(
        database.DateTime,
        nullable=False
    )
    start_time_zone = database.Column(
        database.String(3),
        nullable=False
    )
    end_date = database.Column(
        database.DateTime,
        nullable=False
    )
    end_time_zone = database.Column(
        database.String(3),
        nullable=False
    )
    transaction_date = database.Column(
        database.DateTime,
        nullable=False
    )
    total_duration = database.Column(
        database.Time,
        nullable=False
    )
    charging_time = database.Column(
        database.Time,
        nullable=False
    )
    energy = database.Column(
        database.Float,
        nullable=False
    )
    ghg_savings = database.Column(
        database.Float,
        nullable=False
    )
    gasoline_savings = database.Column(
        database.Float,
        nullable=False
    )
    currency = database.Column(
        database.String(3)
    )
    fee = database.Column(
        database.SmallInteger
    )
    ended_by = database.Column(
        database.String(30)
    )
    user_id = database.Column(
        database.Integer,
        index=True
    )
    driver_postal_code = database.Column(
        database.String(5)
    )
    station_name = database.Column(
        database.String(30),
        index=True,
        primary_key=True
    )
    port_number = database.Column(
        database.SmallInteger,
        nullable=False
    )
    port = database.relationship('Port', backref='events')

    __table_args__ = (
        database.ForeignKeyConstraint(
            ['port_number', 'station_name'],
            ['port.port_number', 'port.station_name']
        ),
    )

    def __repr__(self):
        return '<Event {}, {}>'.format(self.plug_in_event_id, self.station_name)


class EventSchema(marshmallow.SQLAlchemyAutoSchema):
    class Meta:
        model = Event
        load_instance = True

    port = marshmallow.Nested(PortSchema)


event_schema = EventSchema()
events_schema = EventSchema(many=True)
