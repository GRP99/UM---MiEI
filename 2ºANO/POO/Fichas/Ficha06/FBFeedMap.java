import java.time.LocalDateTime;
import java.util.Map;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.List;
import java.util.HashMap;
import java.util.stream.Collectors;

public class FBFeedMap{
    private Map<String, List<FBPost>> fbfmap;
    
    public FBFeedMap(){
        this.fbfmap = new TreeMap<String,List<FBPost>>();
    }
    
    public FBFeedMap(FBFeedMap fb){
        this.fbfmap = fb.getFBFeedMap();
    }
    
    public FBFeedMap(Map<String,List<FBPost>> fb){
        this.fbfmap = fb.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }
    
    public Map<String,List<FBPost>> getFBFeedMap(){
        return this.fbfmap;
    }
    
    public void addPost(String user, FBPost post){
        List<FBPost> postlist = fbfmap.get(user);
        if (postlist.size() == 0){
            postlist = new ArrayList<FBPost>();
            postlist.add(post);
            fbfmap.put(user,postlist);
        }
        else{
            postlist.add(post);
            fbfmap.put(user,postlist);
        }
    }
    
    public void removePosts(String user, LocalDateTime di, LocalDateTime df){
        if(fbfmap.containsKey(user)){
            fbfmap.put(user,fbfmap.get(user).stream().filter(e->e.getData().isBefore(di) && e.getData().isAfter(df)).collect(Collectors.toList()));
        }
    }
    
}
