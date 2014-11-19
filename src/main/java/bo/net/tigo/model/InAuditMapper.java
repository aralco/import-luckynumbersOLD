package bo.net.tigo.model;

/**
 * Created by aralco on 11/18/14.
 */
public class InAuditMapper {
    private String row;

    public InAuditMapper(String row) {
        this.row = row;
    }

    public InAuditMapper() {
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }
}
