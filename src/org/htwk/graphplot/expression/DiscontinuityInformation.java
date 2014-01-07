package org.htwk.graphplot.expression;

public class DiscontinuityInformation
{
    private double fromValue, toValue;
    private boolean isDiscontinuous = false;
    
    public DiscontinuityInformation(double fromValue, double toValue) {
        this.fromValue = fromValue;
        this.toValue = toValue;
    }
    
    public DiscontinuityInformation(double fromValue, double toValue, boolean isDicontinuous) {
        this(fromValue, toValue);
        this.isDiscontinuous = isDicontinuous;
    }
    
    public void setDiscontinuity(boolean isDiscontinuous)
    {
        this.isDiscontinuous = isDiscontinuous;
    }
    
    public boolean isDiscontinuous() {
        return isDiscontinuous;
    }
}
