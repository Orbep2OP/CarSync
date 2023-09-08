package employeeacess;

public enum VehicleEnum {

    TABLE_VEHICLE,
    COLUMN_VEHICLE_MODEL,
    COLUMN_VEHICLE_BRAND,
    COLUMN_VEHICLE_COLOR,
    COLUMN_VEHICLE_REGISTRATION_DATE,
    COLUMN_VEHICLE_PLATE,
    COLUMN_VEHICLE_CATEGORY,
    COLUMN_VEHICLE_NIF,
    COLUMN_VEHICLE_DEACTIVATED,
    INSERT_VEHICLE,
    DELETE_VEHICLE,
    UPDATE_VEHICLE_COLOR,
    QUERY_TABLE_VEHICLE_BY_PLATE,
    QUERY_TABLE_VEHICLE,
    UPDATE_VEHICLE_OWNER,
    DEACTIVATE_VEHICLE,
    COLUMN_VEHICLE_VIN;

    public static String getString(VehicleEnum vps) {

        String s = switch(vps) {
            case TABLE_VEHICLE -> "vehicle";
            case COLUMN_VEHICLE_BRAND -> "brand";
            case COLUMN_VEHICLE_VIN -> "vin";
            case COLUMN_VEHICLE_COLOR -> "color";
            case COLUMN_VEHICLE_MODEL -> "model";
            case COLUMN_VEHICLE_PLATE -> "plate";
            case COLUMN_VEHICLE_CATEGORY -> "category";
            case COLUMN_VEHICLE_NIF -> "nif";
            case COLUMN_VEHICLE_DEACTIVATED -> "deactivated";
            case INSERT_VEHICLE -> "INSERT INTO " + getString(VehicleEnum.TABLE_VEHICLE) + '('
                    + getString(VehicleEnum.COLUMN_VEHICLE_MODEL) + ", " +
                    getString(VehicleEnum.COLUMN_VEHICLE_BRAND) + ", " +
                    getString(VehicleEnum.COLUMN_VEHICLE_COLOR) + ", " +
                    getString(VehicleEnum.COLUMN_VEHICLE_PLATE) + ", " +
                    getString(VehicleEnum.COLUMN_VEHICLE_CATEGORY) + ", " +
                    getString(VehicleEnum.COLUMN_VEHICLE_REGISTRATION_DATE) + ", " +
                    getString(VehicleEnum.COLUMN_VEHICLE_NIF) + ", " +
                    getString(VehicleEnum.COLUMN_VEHICLE_VIN) + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            case DELETE_VEHICLE -> "DELETE FROM " + getString(VehicleEnum.TABLE_VEHICLE) + " WHERE " +
                    getString(VehicleEnum.COLUMN_VEHICLE_PLATE) + " = ?";
            case QUERY_TABLE_VEHICLE_BY_PLATE -> " SELECT * FROM " + getString(VehicleEnum.TABLE_VEHICLE) +
                    " WHERE " + getString(VehicleEnum.COLUMN_VEHICLE_PLATE) + " = ? ";
            case QUERY_TABLE_VEHICLE -> " SELECT * FROM " + getString(VehicleEnum.TABLE_VEHICLE);
            case COLUMN_VEHICLE_REGISTRATION_DATE -> "registration_date";
            case UPDATE_VEHICLE_COLOR -> "UPDATE " + getString(VehicleEnum.TABLE_VEHICLE) + " SET " +
                    getString(VehicleEnum.COLUMN_VEHICLE_COLOR) + " = ?" + " WHERE " + getString(VehicleEnum.COLUMN_VEHICLE_PLATE) + " = ?";
            case UPDATE_VEHICLE_OWNER -> "UPDATE " + getString(VehicleEnum.TABLE_VEHICLE) + " SET " + getString(VehicleEnum.COLUMN_VEHICLE_NIF) + " = ?" + " WHERE " +
                    getString(VehicleEnum.COLUMN_VEHICLE_PLATE) + " = ?";
            case DEACTIVATE_VEHICLE -> "UPDATE " + getString(VehicleEnum.TABLE_VEHICLE) + " SET " +
                    getString(VehicleEnum.COLUMN_VEHICLE_DEACTIVATED) + " = 0  WHERE " +
                    getString(VehicleEnum.COLUMN_VEHICLE_PLATE) + " = ?";
            default -> throw new IllegalArgumentException("No such column or operation for vehicle table");
        };
        return s;
    }

}
