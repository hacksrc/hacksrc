package web_server.data;

public class Color_Enum_Converter
{
    public Color_Enum_Converter()
    {

    }

    public Color_Enums stringToEnum(String color_name)
    {
        Color_Enums return_enum = Color_Enums.OFF;
        if(color_name.toLowerCase() == "off")
        {
            return_enum = Color_Enums.OFF;
        }
        else if(color_name.toLowerCase() == "white")
        {
            return_enum = Color_Enums.WHITE;
        }
        else if(color_name.toLowerCase() == "red")
        {
            return_enum = Color_Enums.RED;
        }
        else if(color_name.toLowerCase() == "green")
        {
            return_enum = Color_Enums.GREEN;
        }
        else if(color_name.toLowerCase() == "blue")
        {
            return_enum = Color_Enums.BLUE;
        }
        else if(color_name.toLowerCase() == "purple")
        {
            return_enum = Color_Enums.PURPLE;
        }
        else if(color_name.toLowerCase() == "yellow")
        {
            return_enum = Color_Enums.YELLOW;
        }
        else if(color_name.toLowerCase() == "orange")
        {
            return_enum = Color_Enums.ORANGE;
        }
        else if(color_name.toLowerCase() == "turquoise")
        {
            return_enum = Color_Enums.TURQUOISE;
        }
        else if(color_name.toLowerCase() == "random")
        {
            return_enum = Color_Enums.RANDOM;
        }

        return return_enum;
    }

}
