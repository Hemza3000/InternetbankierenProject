package sofa.internetbankieren.model;

/**
 * @author Wendy Ellens
 *
 * De sectoren waar zakelijke klanten uit kunnen kiezen bij de registratie.
 */
public enum Sector{
    GEZONDHEIDSZORG_EN_WELZIJN("Gezondheidszorg en welzijn"),
    HANDEL_EN_DIENSTVERLENING("Handel en dienstverlening"),
    ICT("ICT"),
    JUSTITIE_VEILIGHEID_EN_OPENBAAR_BESTUUR("Justitie, veiligheid en openbaar bestuur"),
    LANDBOUW_NATUUR_EN_VISSERIJ("Landbouw, natuur en visserij"),
    MEDIA_EN_COMMUNICATIE("Media en communicatie"),
    ONDERWIJS_CULTUUR_EN_WETENSCHAP("Onderwijs, cultuur en wetenschap"),
    TECHNIEK_PRODUCTIE_EN_BOUW("Techniek, productie en bouw"),
    TOERISME_RECREATIE_EN_HORECA("Toerisme, recreatie en horeca"),
    TRANSPORT_EN_LOGISTIEK("Transport en logistiek");

    private final String naam;

    Sector(final String naam) {
        this.naam = naam;
    }

    public String getNaam() {
        return naam;
    }
}