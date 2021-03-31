import os
from datetime import datetime

import pandas as pd

THIS_FOLDER = os.path.dirname(os.path.abspath(__file__))


def should_init(db):
    is_empty_address = db.session.execute("SELECT * FROM address").first() is None
    is_empty_event = db.session.execute("SELECT * FROM event").first() is None
    is_empty_port = db.session.execute("SELECT * FROM port").first() is None
    is_empty_station = db.session.execute("SELECT * FROM station").first() is None

    return is_empty_address and is_empty_event and is_empty_port and is_empty_station


def parse_date(x):
    return datetime.strptime(x, '%m/%d/%Y %H:%M')


def init_db_data(db):
    my_file = os.path.join(THIS_FOLDER, 'ElectricVehicleChargingStation.csv')

    address_column_names = ['Address 1', 'City', 'State/Province', 'Postal Code', 'Country']
    station_column_names = ['Station Name', 'MAC Address', 'Org Name', 'Latitude', 'Longitude', 'Address 1']
    port_column_names = ['Port Type', 'Port Number', 'Plug Type', 'Station Name']
    event_column_names = ['Plug In Event Id', 'Start Date', 'Start Time Zone', 'End Date', 'End Time Zone',
                          'Transaction Date (Pacific Time)', 'Total Duration (hh:mm:ss)', 'Charging Time (hh:mm:ss)',
                          'Energy (kWh)', 'GHG Savings (kg)', 'Gasoline Savings (gallons)', 'Currency', 'Fee',
                          'Ended By', 'User ID', 'Driver Postal Code', 'Station Name', 'Port Number']

    csv_data = pd.read_csv(my_file, parse_dates=['Start Date', 'End Date', 'Transaction Date (Pacific Time)'],
                           date_parser=parse_date)

    addresses = pd.DataFrame(csv_data, columns=address_column_names).drop_duplicates(keep='last')
    stations = pd.DataFrame(csv_data, columns=station_column_names).drop_duplicates(subset='Station Name', keep='last')
    events = pd.DataFrame(csv_data, columns=event_column_names).drop_duplicates(
        subset=['Plug In Event Id', 'Station Name'], keep='last')
    ports = pd.DataFrame(csv_data, columns=port_column_names).drop_duplicates(keep='last')

    addresses.columns = ['address', 'city', 'state_province', 'postal_code', 'country']
    stations.columns = ['station_name', 'mac_address', 'org_name', 'latitude', 'longitude', 'address_name']
    ports.columns = ['port_type', 'port_number', 'plug_type', 'station_name']
    events.columns = ['plug_in_event_id', 'start_date', 'start_time_zone', 'end_date', 'end_time_zone',
                      'transaction_date', 'total_duration', 'charging_time', 'energy', 'ghg_savings',
                      'gasoline_savings', 'currency', 'fee', 'ended_by', 'user_id',
                      'driver_postal_code', 'station_name', 'port_number']

    addresses.to_sql("address", db.engine, if_exists='append', index=False)
    stations.to_sql("station", db.engine, if_exists='append', index=False)
    ports.to_sql("port", db.engine, if_exists='append', index=False)
    events.to_sql("event", db.engine, if_exists='append', index=False)
