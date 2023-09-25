package client;

/**
 * Перечисление существующих команд
 */
public enum CommandType {
    ADD(new String[]{"askName", "askCoordinates", "askAnnualTurnover", "askFullName", "askEmployeesCount",
            "askType", "askPostalAddress"}),
    CLEAR(new String[]{}),
    UPDATE(new String[]{"askId", "askName", "askCoordinates", "askAnnualTurnover", "askFullName", "askEmployeesCount",
            "askType", "askPostalAddress"}),
    EXIT(new String[]{}),
    SHOW(new String[]{}),
    REMOVE_BY_ID(new String[]{"askId"}),
    EXECUTE_SCRIPT(new String[]{"askFileName"}),
    REMOVE_GREATER(new String[]{}),
    HELP(new String[]{}),
    REMOVE_FIRST(new String[]{}),
    AVERAGE_OF_ANNUAL_TURNOVER(new String[]{}),
    PRINT_UNIQUE_FULL_NAME(new String[]{}),
    COUNT_GREATER_THAN_POSTAL_ADDRESS(new String[]{"askPostalAddress"});

    private final String[] commandFields;

    CommandType(String[] fields) {
        this.commandFields = fields;
    }

    public String[] getCommandFields() {
        return this.commandFields;
    }
}
