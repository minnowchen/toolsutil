package Tools.Crawler;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.Country;

public class Geoip2Util
{
	
	public static void main(String[] args)
	{
		String url = "D:/GeoLite2-Country_20180501/GeoLite2-Country.mmdb";
		String ip = "151.217.178.37";
		try {
			Geoip2Util.getCountry(url, ip);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeoIp2Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static String getCountry(String url, String ip) throws IOException, GeoIp2Exception
	{
		String result = "";
		
			// A File object pointing to your GeoIP2 or GeoLite2 database
			File database = new File(url);

			// This creates the DatabaseReader object. To improve performance, reuse
			// the object across lookups. The object is thread-safe.
			DatabaseReader reader = new DatabaseReader.Builder(database).build();

			InetAddress ipAddress = InetAddress.getByName(ip);

			// Replace "country" with the appropriate method for your database, e.g., "city".
			CountryResponse resp = reader.country(ipAddress);

			Country country = resp.getCountry();                 //[ {"geoname_id":6252001,"is_in_european_union":false,"iso_code":"US","names":{"de":"USA","ru":"США","pt-BR":"Estados Unidos","ja":"アメリカ合衆国","en":"United States","fr":"États-Unis","zh-CN":"美国","es":"Estados Unidos"}} ]
			result = country.getIsoCode();
//			System.out.println(country.getIsoCode());            // 'US'
//			System.out.println(country.getName());               // 'United States'
//			System.out.println(country.getNames().get("zh-CN")); // '美国'
		
		return result;
	}
	
}
