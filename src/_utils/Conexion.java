package _utils;

public record Conexion(int id, Double distancia) {
	public static int cont;

	public static Conexion of(Double distancia) {
		Integer id = cont;
		cont++;
		return new Conexion(id, distancia);
	}

	public static Conexion ofFormat(String[] formato) {
		Double dist = Double.valueOf(formato[2].trim());
		return of(dist);
	}

	@Override
	public String toString() {
		return "id: " + this.id() + "; distancia: " + this.distancia();
	}
}