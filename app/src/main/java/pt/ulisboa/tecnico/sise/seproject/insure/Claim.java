package pt.ulisboa.tecnico.sise.seproject.insure;

public class Claim {
    private String _title;
    private String _plateNumber;
    private String _occurrenceDate;
    private String _description;

    public Claim(String title, String plate_number, String occurDate, String description) {
        this._title = title;
        this._plateNumber = plate_number;
        this._occurrenceDate = occurDate;
        this._description = description;
    }

    public String getTitle() {
        return _title;
    }

    public void setTitle(String _title) {
        this._title = _title;
    }

    public String getPlateNumber() {
        return _plateNumber;
    }

    public void setPlateNumber(String _plateNumber) {
        this._plateNumber = _plateNumber;
    }

    public String getOccurrenceDate() {
        return _occurrenceDate;
    }

    public void setOccurrenceDate(String _occurrenceDate) {
        this._occurrenceDate = _occurrenceDate;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String _description) {
        this._description = _description;
    }

    @Override
    public String toString() {
        return _title;
    }
}
