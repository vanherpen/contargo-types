package net.contargo.types;

/**
 * Validates German {@link LicensePlate}s.
 *
 * <p>Example for a valid German license plate: KA PA 777</p>
 *
 * @author  Aljona Murygina - murygina@synyx.de
 */
class GermanLicensePlateValidator implements LicensePlateValidator {

    /**
     * Validates the given {@link LicensePlate}.
     *
     * <p>A German license plate consists of maximum 8 characters and contains two parts:</p>
     *
     * <ul>
     * <li>the geographic identifier: one, two or three letters (may contain umlauts)</li>
     * <li>the identification numbers and/or letters: one or two letters (no umlauts) and one to four digits (without
     * leading zero)</li>
     * </ul>
     *
     * <p>After the geographic identifier there are the round vehicle safety test and registration seal stickers placed
     * above each other.</p>
     *
     * <p>There are certain license plates that may deviate from the rules above:</p>
     *
     * <ul>
     * <li>Seasonal license plates consists of only maximum 7 characters.</li>
     * <li>License plates of official cars can have up to six digits because of missing identification letters.</li>
     * <li>Classic cars can get an H (historic) at the end of the plate, example: ER A 55H</li>
     * </ul>
     *
     * <p>Note that these special cases are not covered by this validator!</p>
     *
     * <p>Further information: <a href="https://de.wikipedia.org/wiki/Kfz-Kennzeichen_(Deutschland)#Aufbau">License
     * plates of Germany</a></p>
     *
     * @param  licensePlate  to be validated, never {@code null}
     *
     * @return  {@code true} if the given {@link LicensePlate} is valid, else {@code false}
     */
    @Override
    public boolean isValid(LicensePlate licensePlate) {

        String value = licensePlate.getValue();

        /**
         * Normalization of license plate:
         *
         * 1.) upper case: "ka ab123" -> "KA AB123"
         * 2.) use minus instead of whitespace separator: "KA AB123" -> "KA-AB123"
         * 3.) ensure identification numbers are separated from identification letters: "KA-AB123" -> "KA--AB-123"
         * 4.) remove the duplicated minus separators: "KA--AB-123" -> "KA-AB-123"
         *
         * Done!
         */
        String normalizedValue = value.toUpperCase()
                .replaceAll("\\s+", "-")
                .replaceAll("(?<=\\D)(?=\\d)", "-")
                .replaceAll("\\-+", "-");

        return normalizedValue.matches("^[A-ZÄÖÜ]{1,3}\\-[A-Z]{0,2}\\-{0,1}[1-9]{1}[0-9]{0,3}");
    }
}
