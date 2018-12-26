package org.springframework.util;

import org.springframework.lang.Nullable;

import java.util.Comparator;
import java.util.Map;
import java.util.regex.Pattern;

public class AntPathMatcher implements PathMatcher {
    public static final String DEFAULT_PATH_SEPARATOR="/";
    public static final int CACHE_TURNOFF_THRESHOLD=65536;
    private static final Pattern VARIABLE_PATTERN=Pattern.compile("\\{[^/]+?\\}");
    private static final char[] WILDCARD_CHARS={'*','?','{'};
    private String pathSeparator;


    @Override
    public boolean isPattern(String path) {
        return false;
    }

    @Override
    public boolean match(String pattern, String path) {
        return false;
    }

    @Override
    public boolean matchStart(String pattern, String path) {
        return false;
    }

    @Override
    public String extractPathWithinPattern(String pattern, String path) {
        return null;
    }

    @Override
    public Map<String, String> extractUriTemplateVariables(String pattern, String path) {
        return null;
    }

    @Override
    public Comparator<String> getPatternComparator(String path) {
        return null;
    }

    @Override
    public String combine(String pattern1, String pattern2) {
        return null;
    }


    protected static class AntPatternComparator implements Comparator<String >{
        private final String path;

        public AntPatternComparator(String path) {
            this.path = path;
        }

        @Override
        public int compare(String o1, String o2) {
            PatternInfo info1=new PatternInfo(o1);
            PatternInfo info2=new PatternInfo(o2);
            if(info1.isLeastSpecific()&&info2.isLeastSpecific()){
                return 0;
            }else if(info1.isLeastSpecific()){
                return 1;
            }else if(info2.isLeastSpecific()){
                return -1;
            }
//            boolean pattern1EqualsPath = pattern1.equals(path);
//            boolean pattern2EqualsPath = pattern2.equals(path);
            return 0;
        }
    }

    private static class PatternInfo{
        @Nullable
        private final String pattern;
        private int uriVars;
        private int singleWildcards;
        private int doubleWildcards;
        private boolean catchAllPattern;
        private boolean prefixPattern;
        @Nullable
        private Integer length;

        public PatternInfo(@Nullable String pattern) {
            this.pattern = pattern;
            if(this.pattern!=null) {
                initCounters();
                this.catchAllPattern = this.pattern.equals("/**");
                this.prefixPattern = !this.catchAllPattern && this.pattern.endsWith("/**");
            }
            if(this.uriVars==0){
                this.length=(this.pattern!=null?this.pattern.length():0);
            }

        }

        private void initCounters() {
            int pos=0;
            if(this.pattern!=null){
                while (pos<this.pattern.length()){
                    if(this.pattern.charAt(pos)=='{'){
                        this.uriVars++;
                        pos++;
                    }else if(this.pattern.charAt(pos)=='*'){
                        if(pos+1<this.pattern.length()&&this.pattern.charAt(pos+1)=='*'){
                            this.doubleWildcards++;
                            pos+=2;
                        }else if(pos>0&&!this.pattern.substring(pos-1).equals(".*")){
                            this.singleWildcards++;
                            pos++;
                        }else{
                            pos++;
                        }
                    }else{
                        pos++;
                    }
                }
            }
        }
        public int getUriVars(){return this.uriVars;}
        public int getSingleWildcards(){return  this.singleWildcards;}
        public int getDoubleWildcards(){return this.doubleWildcards;}
        public boolean isLeastSpecific(){return (this.pattern==null||this.catchAllPattern);}
        public boolean isPrefixPattern(){return  this.prefixPattern;}
        public int getTotalCount(){return  this.uriVars+this.singleWildcards+(2*doubleWildcards);}

        public Integer getLength() {
            if(this.length==null){
                this.length=(this.pattern!=null?VARIABLE_PATTERN.matcher(this.pattern).replaceAll("#").length():0);
            }
            return this.length;
        }
    }
    private static class PathSeparatorPatternCache{
        private final String endsOnWildCard;
        private final String  endsOnDoubleWildCard;

        public PathSeparatorPatternCache(String pathSeparator) {
            this.endsOnWildCard = pathSeparator+"*";
            this.endsOnDoubleWildCard = pathSeparator+"**";
        }

        public String getEndsOnWildCard() {
            return endsOnWildCard;
        }

        public String getEndsOnDoubleWildCard() {
            return endsOnDoubleWildCard;
        }
    }
}
