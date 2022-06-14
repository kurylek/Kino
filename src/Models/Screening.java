package Models;

import Enums.EnumScreeningStatus;
import java.math.BigDecimal;
import java.util.Date;

public class Screening {
    static int minViewerCount = 10;

    private Date screeningDate; //dd-MM-yyyy gg:mm
    private int viewerCount;
    private BigDecimal baseTicketPrice;
    private EnumScreeningStatus screeningStatus;
}