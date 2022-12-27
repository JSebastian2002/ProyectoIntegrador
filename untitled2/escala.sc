val bufferedSource = io.Source.fromFile("/home/frank/Documentos/tercero/Programacion/movie_dataset (1).csv")
val dataGoles = bufferedSource.getLines()
  .drop(1)
  .map(_.split(";"))
  .map{case Array(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p) => (a, b, c, d, e, f, g, h.toInt, i.toInt, j, k, l.toInt, m.toInt, n, o.toInt, p.toInt)}
  .toList
val totGol = dataGoles.map(x=>x ._8).sum