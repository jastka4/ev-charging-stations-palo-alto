from flask import jsonify, request, escape

from app.models import *
from . import app, database


@app.route('/')
def index():
    return "It's not a bug, it's a feature! :D"


@app.route('/event', methods=['GET'])
def get_latest_events():
    n = escape(request.args.get('n'))
    station_name = escape(request.args.get('station'))

    result = database.session \
        .query(Event) \
        .filter(Event.station_name == station_name) \
        .limit(n)

    return jsonify(events_schema.dump(result))


@app.route('/event', methods=['POST'])
def add_event():
    port_number = request.json.pop('port_number')
    station_name = request.json.pop('station_name')
    new_event = event_schema.load(request.json)
    new_event.station_name = station_name
    new_event.port_number = port_number

    if Station.query.filter(Station.station_name == station_name).first() is None:
        return 'Station does not exist!', 400
    if Port.query.filter(Port.port_number == port_number, Port.station_name == station_name).first() is None:
        return 'Port does not exist!', 400

    event_result = Event.query \
        .filter(Event.plug_in_event_id == request.json['plug_in_event_id'],
                Event.station_name == station_name) \
        .first()
    if event_result is not None:
        return 'Event already exists!', 400
    else:
        database.session.add(new_event)
        database.session.commit()
        return jsonify(event_schema.dump(new_event)), 201


@app.route('/event/consumption', methods=['GET'])
def get_event_for_user_and_energy_consumption():
    user_id = escape(request.args.get('user'))
    consumption_start = escape(request.args.get('start'))
    consumption_end = escape(request.args.get('end'))

    result = database.session \
        .query(Event) \
        .filter(Event.user_id == user_id,
                Event.energy >= consumption_start,
                Event.energy <= consumption_end) \
        .all()

    return jsonify(events_schema.dump(result))


@app.route('/energy/consumption-details', methods=['GET'])
def get_energy_consumption():
    result = database.session \
        .query(database.func.sum(Event.energy), database.func.avg(Event.energy)) \
        .one_or_none()

    return jsonify(sum=round(result[0], 3), avg=round(result[1], 3))


@app.route('/energy/consumption', methods=['GET'])
def get_biggest_energy_consumption():
    start_date = escape(request.args.get('start'))
    end_date = escape(request.args.get('end'))

    result = database.session \
        .query(Event.end_date, Event.user_id, database.func.max(Event.energy)) \
        .filter(Event.start_date >= start_date,
                Event.end_date <= end_date) \
        .group_by(database.func.date(Event.end_date)) \
        .all()

    return jsonify(events_schema.dump(result))
