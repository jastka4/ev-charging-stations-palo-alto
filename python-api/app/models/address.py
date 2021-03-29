from app import database, marshmallow


class Address(database.Model):
    __tablename__ = 'address'

    address = database.Column(
        database.String(30),
        primary_key=True
    )
    city = database.Column(
        database.String(10),
        nullable=False
    )
    state_province = database.Column(
        database.String(10),
        nullable=False
    )
    postal_code = database.Column(
        database.String(5),
        nullable=False
    )
    country = database.Column(
        database.String(15),
        nullable=False
    )

    def __repr__(self):
        return '<Address {}>'.format(self.address)


class AddressSchema(marshmallow.SQLAlchemyAutoSchema):
    class Meta:
        model = Address
        load_instance = True


address_schema = AddressSchema()
addresses_schema = AddressSchema(many=True)
