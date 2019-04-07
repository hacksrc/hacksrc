package web_server.data;

public class Effect_Enum_Converter
{
    public Effect_Enum_Converter()
    {

    }

    public Effects_Enum stringToEnum(String color_name)
    {
        Effects_Enum return_enum = Effects_Enum.OFF;
        if(color_name.toLowerCase() == "off")
        {
            return_enum = Effects_Enum.OFF;
        }
        else if(color_name.toLowerCase() == "fire")
        {
            return_enum = Effects_Enum.FIRE;
        }
        else if(color_name.toLowerCase() == "ice")
        {
            return_enum = Effects_Enum.ICE;
        }
        else if(color_name.toLowerCase() == "level up")
        {
            return_enum = Effects_Enum.LEVEL_UP;
        }
        else if(color_name.toLowerCase() == "solid color")
        {
            return_enum = Effects_Enum.SOLID_COLOR;
        }

        return return_enum;
    }

}
