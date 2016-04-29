package cn.com.genechem.gfa.utils;

import java.util.*;

/**
 * Created by xxue on 16/4/28.
 */
public class TaskGraph {
    private Graph tscGraph = new Graph();

    //相邻节点
    private Map<Vertex, Vertex[]> tscEdgeMap = tscGraph.getAdjacencies();

    //得到set,有向图的各个点
    Set<Vertex> tscVertexSet = tscGraph.getVertexSet();
    Hashtable<String, Vertex> htVertex = new Hashtable<>();

    public void calculate(){

        //有向图各个点的名称
        Vertex vertex = new Vertex("name");
        tscVertexSet.add(vertex);
        //对应名字和对应有向图的节点
        htVertex.put(vertex.getName(), vertex);
        //TODO
        //对应名字和相应的有向图的节点，建立hashtable关系
        Hashtable<String, Vertex> htDestNodes = new Hashtable<>();
        htDestNodes.put("name",htVertex.get("name"));

        //形成图结构
        Collection<Vertex> values = htDestNodes.values();
        tscEdgeMap.put(vertex, values.toArray(new Vertex[values.size()]));


        //根据有向图关系得到遍历顺序
        for (Vertex v : topologicalSort(tscGraph)) {
            Vertex[] dependencies = tscEdgeMap.get(v);
            if(dependencies==null){
                //TODO
            }else{
                for(Vertex v2:dependencies){

                }
            }
        }
    }


    private static Vertex[] topologicalSort(Graph graph) {
        Set<Vertex> vertexSet = graph.getVertexSet();
        if (vertexSet.size() < 2) {
            return vertexSet.toArray(new Vertex[vertexSet.size()]);
        }

        LinkedList<Vertex> sortedList = new LinkedList<>();
        TimeRecorder recorder = new TimeRecorder();

        for (Vertex vertex : vertexSet) {
            if (vertex.color == Vertex.WHITE) {
                visitVertex(graph, vertex, recorder, sortedList);
            }
        }
        return sortedList.toArray(new Vertex[sortedList.size()]);
    }
    private static void visitVertex(Graph graph, Vertex vertex, TimeRecorder recorder, LinkedList<Vertex> sortedList) {
        recorder.time += 1;
        vertex.color = Vertex.GRAY;
        vertex.discover = recorder.time;

        Map<Vertex, Vertex[]> edgeMap = graph.getAdjacencies();
        Vertex[] adjacencys = edgeMap.get(vertex);
        if (adjacencys != null && adjacencys.length > 0) {
            for (Vertex adjacency : adjacencys) {
                if (adjacency.color == Vertex.WHITE) {
                    adjacency.parent = vertex;
                    visitVertex(graph, adjacency, recorder, sortedList);
                }
            }
        }
        recorder.time += 1;
        vertex.color = Vertex.BLACK;
        vertex.finish = recorder.time;
        sortedList.addLast(vertex);
    }

    static class TimeRecorder {
        private int time = 0;

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }

    private static class Vertex {
        private static int WHITE = 0;
        private static int GRAY = 1;
        private static int BLACK = 2;

        private String name;
        private int color;
        private Vertex parent;
        private int discover;
        private int finish;

        public Vertex(String name) {
            this.name = name;
            this.color = WHITE;
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public Vertex getParent() {
            return parent;
        }

        public void setParent(Vertex parent) {
            this.parent = parent;
        }

        public int getDiscover() {
            return discover;
        }

        public void setDiscover(int discover) {
            this.discover = discover;
        }

        public int getFinish() {
            return finish;
        }

        public void setFinish(int finish) {
            this.finish = finish;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Vertex other = (Vertex) obj;
            if (name == null) {
                if (other.name != null)
                    return false;
            }
            else if (!name.equals(other.name))
                return false;
            return true;
        }
    }
    private static class Graph {
        private Set<Vertex> vertexSet = new HashSet<>();
        // 相邻的节点
        private Map<Vertex, Vertex[]> adjacencies = new HashMap<>();

        public Set<Vertex> getVertexSet() {
            return vertexSet;
        }

        public void setVertexSet(Set<Vertex> vertexSet) {
            this.vertexSet = vertexSet;
        }

        public Map<Vertex, Vertex[]> getAdjacencies() {
            return adjacencies;
        }

        public void setAdjacencies(Map<Vertex, Vertex[]> adjacencies) {
            this.adjacencies = adjacencies;
        }
    }

}
