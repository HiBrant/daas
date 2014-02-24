package data.as.a.service.convert;

public interface Converter<S, T, E extends Throwable> {

	T convert(S source) throws E;
}
