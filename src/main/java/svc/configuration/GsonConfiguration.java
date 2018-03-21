package svc.configuration;

import java.io.IOException;
import java.util.List;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.http.converter.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

@Configuration
@EnableWebMvc
public class GsonConfiguration implements WebMvcConfigurer {
	
	@Override
	 public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
       converters.add(createGsonHttpMessageConverter());
	 }

	 private GsonHttpMessageConverter createGsonHttpMessageConverter() {
		Gson gsonWithConverter = new GsonBuilder()
									.registerTypeAdapter(LocalDate.class, new LocalDateJsonAdapter())
									.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeJsonAdapter())
									.create();

        GsonHttpMessageConverter gsonConverter = new GsonHttpMessageConverter();
        gsonConverter.setGson(gsonWithConverter);

        return gsonConverter;
	 }
	
	private class LocalDateJsonAdapter extends TypeAdapter<LocalDate>{

		@Override
		public void write(JsonWriter out, LocalDate value) throws IOException {
			if (value != null){
				out.value(value.toString());
			}else{
				out.value("");
			}
			
		}

		@Override
		public LocalDate read(JsonReader in) throws IOException {
			String dateString = in.nextString();
			if (dateString != "" && dateString != null){
				return LocalDate.parse(dateString,DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			}else{
				return null;
			}
		}
	}
	
	private class LocalDateTimeJsonAdapter extends TypeAdapter<LocalDateTime>{

		@Override
		public void write(JsonWriter out, LocalDateTime value) throws IOException {
			if (value !=null){
				out.value(value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
			}else{
				out.value("");
			}
			
		}

		@Override
		public LocalDateTime read(JsonReader in) throws IOException {
			String dateTimeString = in.nextString();
			if (dateTimeString != "" && dateTimeString != null)
				return LocalDateTime.parse(dateTimeString,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			else
				return null;
		}

	}
	
}
