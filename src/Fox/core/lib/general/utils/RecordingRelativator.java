package Fox.core.lib.general.utils;

import Fox.core.lib.services.acoustid.LookupByFP.sources.Recording;

import static java.lang.Math.abs;

public class RecordingRelativator implements Relativator<Recording, Integer, Float>
{
    @Override
    public Float RelativeCompare(Recording o1, Integer o2)
            throws IllegalArgumentException
    {
        if (o1 == null || !o1.hasDuration() || o1.getDuration() <= 0 ||  o2 == null || o2 <= 0)
            throw new IllegalArgumentException();

        Integer duration = o1.getDuration();

        return (float)(100*(abs(duration - o2))/duration);
    }
}
