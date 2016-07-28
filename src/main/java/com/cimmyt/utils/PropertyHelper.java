/*
Copyright 2013 International Maize and Wheat Improvement Center
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
       http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.cimmyt.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class PropertyHelper {
	public enum Bundle{msg, conf, img}
    public enum Languages {english, spanish}
    public enum Configs {config} //the name of config.properties
    public enum Images {images} //the name of images.properties
    public enum LdapPlugin {ldapPlugin}//the name of ldapPlugin.properties
    
    private Languages messagesBundle;
    private Configs configBundle;
     
    private ResourceBundle bundleMessages;
    private ResourceBundle bundleConfig;
    private ResourceBundle bundleImages;
 
    public PropertyHelper()
    {
        messagesBundle = Languages.english;
        configBundle = Configs.config;
        reloadBundles();
    }
  
    /**
     * Defines the name of the .properties file
     */
    public void reloadBundles()
    {
        bundleMessages = ResourceBundle.getBundle( messagesBundle.toString() );
        bundleConfig = ResourceBundle.getBundle( configBundle.toString() );
        bundleImages = ResourceBundle.getBundle( Images.images.toString() );
    }
    
    
    public String getKey( String name )
    {
        try
        {
            reloadBundles();
            return bundleMessages.getString( name );
        }
        catch( Exception e )
        {
            return null;
        }
    }

    public String getKey( String name, Bundle bundle )
    {
        try
        {
            switch( bundle )
            {
                case msg:
                    return bundleMessages.getString( name ).trim();
                case conf:
                    return bundleConfig.getString( name ).trim();
                case img:
                    return bundleImages.getString( name ).trim();
                default:
                    return null;
            }

        }
        catch( Exception e )
        {
            return null;
        }
    }

    public String getKey( String name, String[] params )
    {
        try
        {

            String prop = bundleMessages.getString( name );
            for( int i = 0; i < params.length; i++ )
            {
                prop = prop.replaceFirst( "[{]" + i + "[}]", params[i] );
            }
            return prop;
        }
        catch( Exception e )
        {
            return null;
        }
    }

    public Languages getMessagesBundle()
    {
        return messagesBundle;
    }

    public void setMessagesBundle( Languages messagesBundle )
    {
        this.messagesBundle = messagesBundle;
    }

    public static String[] getLanguages()
    {
        List<String> list = new ArrayList<String>();
        Languages[] langs = Languages.values();
        for( Languages lang : langs )
        {
            list.add( lang.toString() );
        }
        
        String res[] = new String[list.size()];
        list.toArray( res );
        
        return res;
    }

    public Languages  setLanguageToLocale(){
    	Locale locale = Locale.getDefault();
    	if (locale.getCountry().equals(Constants.LocaleEnglish)){
    		return Languages.english;
    	}else if (locale.getCountry().equals(Constants.LocaleSpanish)){
    		return Languages.spanish;
    	}
    	return Languages.english;
    }

}
