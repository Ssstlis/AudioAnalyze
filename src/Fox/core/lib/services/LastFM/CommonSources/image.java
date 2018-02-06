package Fox.core.lib.services.LastFM.CommonSources;

import Fox.core.lib.general.DOM.Extract;
import Fox.core.lib.general.utils.Exceptions;

import java.util.ArrayList;
import java.util.List;

public class image
{
    private String text;
    private String size;

    public image()
    {

    }

    public image(
            String text,
            String size)
    {
        this.size = size;
        this.text = text;
    }

    public image(image copy)
    {
        if (copy != null)
        {
            this.size = copy.size;
            this.text = copy.text;
        }
    }

    public static List<image> imageListCopy(List<image> copy)
    {
        List<image> temp = null;

        if (copy != null)
        {
            temp = new ArrayList<>();
            for (image elem : copy)
            {
                temp.add(new image(elem));
            }
        }

        return temp;
    }

    public static Extract extract(List<image> imageList)
            throws
            Exceptions.NoMatchesException
    {
        if (imageList != null && !imageList.isEmpty())
        {
            String text = null;
            String sizes = null;

            for (int i = imageList.size() - 1; i >= 0; i--)
            {
                image image = imageList.get(i);
                if (image.hasText())
                {
                    text = image.getText();
                    sizes = image.getSize();
                    break;
                }
            }

            if (text == null)
            {
                throw new Exceptions.NoMatchesException("No matches.");
            }

            Extract temp = new Extract(text, sizes);
            return temp;
        }
        return null;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getSize()
    {
        return size;
    }

    public void setSize(String size)
    {
        this.size = size;
    }

    public boolean hasText()
    {
        return (text != null && !text.isEmpty());
    }

    public boolean hasSize()
    {
        return (size != null && !size.isEmpty());
    }
}
