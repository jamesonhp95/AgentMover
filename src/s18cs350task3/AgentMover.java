package s18cs350task3;

/**
 * @author Jameson Price
 *
 *  A simple java prototype for creating and manipulating arbitrary physical agents in three-dimensional space
 *  An agent's target speed, altitude, and azimuth can be set, which causes the actual speed to accelerate or
 *  decelerate toward target by a delta speed, and likewise the altitude to move toward the target by the altitude
 *  delta, and azimuth by a delta angle, upon a call to update_(). The new position is based on the change in position
 *  from the old position with the new speed and azimuth.
 */
public class AgentMover implements I_Updateable
{
    private double x;
    private double y;
    private double z;
    private double speed;
    private double speedDeltaAccelerate;
    private double speedDeltaDecelerate;
    private double speedMax;
    private double speedMin;
    private double speedTarget;
    private double azimuth;
    private double azimuthDelta;
    private double azimuthTarget;
    private boolean isClockwise;
    private double altitudeDelta;
    private double altitudeTarget;

    /**
     *  This is the constructor of the AgentMover class
     * @param x --- A double that is the x coordinate of the agent
     * @param y --- A double that is the y coordinate of the agent
     * @param z --- A double that is the z coordinate and altitude of the agent
     * @param speed --- A double that is the current speed of the agent
     * @param speedDeltaAccelerate --- A double that is the maximum accelerating speed of the agent
     * @param speedDeltaDecelerate --- A double that is the maximum decelerating speed of the agent
     * @param speedMax --- A double that is the maximum speed that the agent may attain
     * @param azimuth --- A double that is the current azimuth of the agent
     * @param azimuthDelta --- A double that is the maximum delta angle of the agent
     * @param altitudeDelta --- A double that is the maximum change in altitude of the agent
     * @throws RuntimeException
     */
    public AgentMover(double x,
                double y,
                double z,
                double speed,
                double speedDeltaAccelerate,
                double speedDeltaDecelerate,
                double speedMax,
                double azimuth,
                double azimuthDelta,
                double altitudeDelta) throws RuntimeException
    {
        if(speed < 0 || speedDeltaAccelerate < 0 || speedDeltaDecelerate < 0 || speedMax < speed || azimuthDelta < 0 || azimuthDelta >= 360 || azimuth >= 360 || azimuth < 0 || altitudeDelta < 0)
            throw new RuntimeException("Cannot have Speed, SpeedDeltaAccelerate, speedDeltaDecelerate, azimuthDelta, altitudeDelta less than 0 or speedMax less than speed");
        this.x = x;
        this.y = y;
        this.z = z;
        this.altitudeTarget = z;
        this.speed = speed;
        this.speedDeltaAccelerate = speedDeltaAccelerate;
        this.speedDeltaDecelerate = speedDeltaDecelerate;
        this.speedMax = speedMax;
        this.speedTarget = speed;
        this.azimuth = azimuth;
        this.azimuthTarget = azimuth;
        this.isClockwise = true;
        this.azimuthDelta = azimuthDelta;
        this.altitudeDelta = altitudeDelta;
        this.speedMin = 0;
    }

    /**
     *  Gets the x position of the agent.
     * @return a double that represents the x coordinate of the agent
     */
    public double getX()
    {
        return this.x;
    }

    /**
     * Gets the y position of the agent.
     * @return a double that represents the y coordinate of the agent
     */
    public double getY()
    {
        return this.y;
    }

    /**
     * Gets the z position of the agent.
     * @return a double that represents the z coordinate of the agent
     */
    public double getZ()
    {
        return this.z;
    }

    /**
     * Gets the current speed of the agent
     * @return a double that represents the current speed of the agent
     */
    public double getSpeed()
    {
        return this.speed;
    }

    /**
     * Gets the delta speed of the agent for acceleration to the target speed
     * @return a double that represents the delta acceleration of the agent
     */
    public double getSpeedDeltaAccelerate()
    {
        return this.speedDeltaAccelerate;
    }

    /**
     * Gets the delta speed of the agent for deceleration to the target speed
     * @return a double that represents the delta deceleration of the agent
     */
    public double getSpeedDeltaDecelerate()
    {
        return this.speedDeltaDecelerate;
    }

    /**
     * Gets the maximum speed the agent may attain.
     * @return a double that represents the maximum speed of the agent
     */
    public double getSpeedMax()
    {
        return this.speedMax;
    }

    /**
     * Gets the minimum speed the agent may attain
     * @return a double that represents the minimum speed of the agent
     */
    public double getSpeedMin()
    {
        return this.speedMin;
    }

    /**
     * Gets the speed the agent must attain
     * @return a double that represents the target speed for the agent
     */
    public double getSpeedTarget()
    {
        return this.speedTarget;
    }

    /**
     * Gets the current azimuth of the agent
     * @return a double that represents the azimuth of the agent
     */
    public double getAzimuth()
    {
        return this.azimuth;
    }

    /**
     * Gets the angle by which the azimuth changes toward the target azimuth
     * @return a double that represents the delta angle that the agent can change toward the target azimuth
     */
    public double getAzimuthDelta()
    {
        return this.azimuthDelta;
    }

    /**
     * Gets the azimuth the agent must attain
     * @return a double that represents the target azimuth of the agent
     */
    public double getAzimuthTarget()
    {
        return this.azimuthTarget;
    }

    /**
     * Gets the rate by which the altitude changes toward the target altitude
     * @return a double that represents the maximum rate the altitude may change for the agent
     */
    public double getAltitudeDelta()
    {
        return altitudeDelta;
    }

    /**
     * Sets the altitude the agent must attain
     * @param altitude --- A double that is the new target altitude that the agent must attain
     * @throws RuntimeException
     */
    public void setAltitudeTarget(double altitude) throws RuntimeException
    {
        this.altitudeTarget = altitude;
    }

    /**
     * Sets the azimuth the agent must attain
     * @param azimuth --- A double that is the new target azimuth that the agent must attain
     * @param isClockwise --- A boolean representation of whether the agent turns clockwise
     *                    or counterclockwise to achieve the new target azimuth
     * @throws RuntimeException
     */
    public void setAzimuthTarget(double azimuth, boolean isClockwise) throws RuntimeException
    {
        if(azimuth < 0 || azimuth >= 360)
            throw new RuntimeException("Azimuth cannot be less than 0");
        this.azimuthTarget = azimuth;
        this.isClockwise = isClockwise;
    }

    /**
     * Sets the speed the agent must attain
     * @param speed --- A double that is the new target speed that the agent must attain
     * @throws RuntimeException
     */
    public void setSpeedTarget(double speed) throws RuntimeException
    {
        if(speed < 0 || speed > getSpeedMax())
            throw new RuntimeException("Speed cannot be less than 0 or greater than speedMax");
        this.speedTarget = speed;

    }

    /**
     * Gets whether the target altitude has been attained
     * @return a boolean that is true if the target altitude has been attained and false otherwise
     */
    public boolean hasAttainedTargetAltitude()
    {
        return getZ() == this.altitudeTarget;
    }

    /**
     * Gets whether the target azimuth has been attained
     * @return a boolean that is true if the target azimuth has been attained and false otherwise
     */
    public boolean hasAttainedTargetAzimuth()
    {
        return getAzimuth() == getAzimuthTarget();
    }

    /**
     * Gets whether the target speed has been attained
     * @return a boolean that is true if the target speed has been attained and false otherwise
     */
    public boolean hasAttainedTargetSpeed()
    {
        return getSpeed() == getSpeedTarget();
    }

    /**
     * Gets whether to turn clockwise (otherwise counterclockwise) toward the target azimuth
     * @return a boolean that is true if the agent turns clockwise toward the target azimuth
     *          and false otherwise
     */
    public boolean isAzimuthTargetClockwise()
    {
            return this.isClockwise;
    }

    /**
     * Determines whether the agent is within a certain distance of a point
     * @param x --- A double that is the x coordinate of the point
     * @param y --- A double that is the y coordinate of the point
     * @param z --- A double that is the z coordinate of the point
     * @param distance --- A double that is the certain distance we need to compare to
     * @return a boolean that is true if the agent is within a certain distance of a point
     *          and false otherwise
     * @throws RuntimeException
     */
    public boolean isProximate(double x, double y, double z, double distance) throws RuntimeException
    {
        if(distance < 0)
            throw new RuntimeException("Distance cannot be less than 0");
        double xComponents = (x - getX())*(x - getX());
        double yComponents = (y - getY())*(y - getY());
        double zComponents = (z - getZ())*(z - getZ());
        double currentDistance = Math.sqrt((xComponents+yComponents+zComponents));
        if(currentDistance <= distance)
            return true;
        else
            return false;
    }

    /**
     * Updates the current azimuth, speed, and position of the agent.
     */
    public void update_()
    {
        updateAzimuth();
        updateSpeed();
        updatePosition();
    }

    /**
     * Updates the current position of the agent
     */
    private void updatePosition()
    {
        if(!hasAttainedTargetAltitude())
        {
            if(getZ() < this.altitudeTarget)
            {
                if (getZ() + getAltitudeDelta() < this.altitudeTarget)
                    this.z = getZ() + getAltitudeDelta();
                else
                    this.z = this.altitudeTarget;
            }
            else
            {
                if(getZ() - getAltitudeDelta() > this.altitudeTarget)
                    this.z = getZ() - getAltitudeDelta();
                else
                    this.z = this.altitudeTarget;
            }
        }

        this.x = getX() + (getSpeed() * Math.sin(Math.toRadians(getAzimuth())));
        this.y = getY() + (getSpeed() * Math.cos(Math.toRadians(getAzimuth())));
    }

    /**
     * Updates the current speed of the agent
     */
    private void updateSpeed()
    {
        if(!hasAttainedTargetSpeed())
        {
            if(getSpeed() < getSpeedTarget())
            {
                if ((getSpeedTarget() - getSpeed()) < getSpeedDeltaAccelerate())
                    this.speed = getSpeedTarget();
                else
                    this.speed = getSpeed() + getSpeedDeltaAccelerate();
            }
            else {
                if ((getSpeed() - getSpeedTarget()) < getSpeedDeltaDecelerate())
                    this.speed = getSpeedTarget();
                else
                    this.speed = getSpeed() - getSpeedDeltaDecelerate();
            }
        }
    }

    /**
     * Updates the current azimuth of the agent
     */
    private void updateAzimuth()
    {
        boolean norm = true;
        if(!hasAttainedTargetAzimuth())
        {
            if(getAzimuthTarget() > getAzimuth() && !isAzimuthTargetClockwise() && ((getAzimuth() - getAzimuthDelta()+360) > getAzimuthTarget()))
                norm = false;
            if(getAzimuthTarget() < getAzimuth() && isAzimuthTargetClockwise() && ((getAzimuth() + getAzimuthDelta() -360) < getAzimuthTarget()))
                norm = false;
            if(!isAzimuthTargetClockwise())
            {
                if((((getAzimuth() - getAzimuthDelta())) > getAzimuthTarget()) || !norm)
                    this.azimuth = (getAzimuth() + 360 - getAzimuthDelta())%360;
                else
                    this.azimuth = getAzimuthTarget();
            }
            else
            {
                if ((((getAzimuth() + getAzimuthDelta())) < getAzimuthTarget()) || !norm)
                    this.azimuth = (getAzimuth() + getAzimuthDelta())%360;
                else
                    this.azimuth = getAzimuthTarget();
            }
        }
    }

    /**
     * An appropriate toString for an agent to show its exact state when called
     * @return a String that gives details about the agents current position (x, y, z)
     *          Current speed, delta acceleration, delta deceleration, speed target,
     *          max speed, min speed, current azimuth, azimuth delta, azimuth target,
     *          altitude delta, and altitude target.
     */
    @Override
    public String toString()
    {
        String str = "X Position: ";
        str = str + getX() + "\nY Position: " + getY() + "\nZ Position: " + getZ();
        str = str + "\nCurrent Speed: " + getSpeed() + "\nSpeed Change in Acceleration: " + getSpeedDeltaAccelerate()+ "\nSpeed change in Deceleration: " + getSpeedDeltaDecelerate() + "\nSpeed Target: " + getSpeedTarget();
        str = str + "\nMax Speed: " + getSpeedMax() + "\nMin Speed: " + getSpeedMin() + "\nCurrent Azimuth: " + getAzimuth() + "\nAzimuth Delta: " + getAzimuthDelta() + "\nAzimuth Target: " + getAzimuthTarget();
        str = str + "\nAltitude Delta: " + getAltitudeDelta() + "\nAltitude Target: " + this.altitudeTarget;
        return str;
    }
}
