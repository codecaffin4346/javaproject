import java.io.*;
import java.net.InetSocketAddress;
import java.util.List;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class HealthWebServer {

    public static void main(String[] args) throws IOException {
        int port = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new HealthHandler());
        server.setExecutor(null);
        System.out.println("Server running at http://localhost:" + port);
        server.start();
    }

    static class HealthHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {
            List<HealthRecord> records = DataManager.readData("data/health_records.txt");

            int highBP = 0;
            int totalPulse = 0;
            for (HealthRecord r : records) {
                if (r.getBP() > 140) highBP++;
                totalPulse += r.getPulse();
            }
            double avgPulse = totalPulse / (double) records.size();

            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html><html><head><meta charset='UTF-8'>")
                .append("<title>Student Health Dashboard</title>")
                .append("<script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>")
                .append("<script type=\"text/javascript\">")
                .append("google.charts.load('current', {packages:['corechart']});")
                .append("google.charts.setOnLoadCallback(drawChart);")
                .append("function drawChart() {")
                .append("var data = google.visualization.arrayToDataTable([")
                .append("['Category', 'Count'],")
                .append("['High BP (>140)', ").append(highBP).append("],")
                .append("['Normal BP', ").append(records.size() - highBP).append("]")
                .append("]);")
                .append("var options = { title: 'BP Distribution', pieHole: 0.4 };")
                .append("var chart = new google.visualization.PieChart(document.getElementById('donutchart'));")
                .append("chart.draw(data, options);")
                .append("}")
                .append("</script>")
                .append("<style>")
                .append("body { font-family: Arial, sans-serif; background: #f4f4f4; padding: 20px; }")
                .append("h1, h2 { text-align: center; }")
                .append(".summary { background: #fff; padding: 15px; border-radius: 10px; width: 60%; margin: auto; box-shadow: 0 0 10px #ccc; }")
                .append("table { width: 80%; margin: 20px auto; border-collapse: collapse; }")
                .append("th, td { padding: 10px; border: 1px solid #ccc; text-align: center; }")
                .append("th { background: #007BFF; color: white; }")
                .append("</style>")
                .append("</head><body>")
                .append("<h1>Student Health Dashboard</h1>")
                .append("<div class='summary'>")
                .append("<h2>Summary</h2>")
                .append("<p><strong>Average Pulse:</strong> ").append(String.format("%.2f", avgPulse)).append("</p>")
                .append("<p><strong>Students with High BP:</strong> ").append(highBP).append("</p>")
                .append("<p><strong>Total Students:</strong> ").append(records.size()).append("</p>")
                .append("</div>")
                .append("<div id='donutchart' style='width: 600px; height: 400px; margin: auto;'></div>")
                .append("<table><tr><th>Name</th><th>Age</th><th>Pulse</th><th>BP</th></tr>");

            for (HealthRecord r : records) {
                html.append("<tr>")
                    .append("<td>").append(r.getName()).append("</td>")
                    .append("<td>").append(r.getAge()).append("</td>")
                    .append("<td>").append(r.getPulse()).append("</td>")
                    .append("<td>").append(r.getBP()).append("</td>")
                    .append("</tr>");
            }

            html.append("</table></body></html>");

            byte[] bytes = html.toString().getBytes();
            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        }
    }
}
