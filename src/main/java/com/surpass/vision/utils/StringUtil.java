package com.surpass.vision.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.codec.EncodingException;


 ///
  // 字符串处理方法大全
     // 主要方法包括：
     // <ul>
     // <li>unicodeToGB －由"GBK"码转换为"ISO8859_1"码
     // <li>GBToUnicode －由"ISO8859_1"码转换为"GBK"码
     // <li>replace - 在字符串中进行查找替换，给出替换后的字符串(update)
     // <li>split - 使用给定的分隔符拆分字符串成字符串数组(new);
     // <li>join - 组合数据结构中所有元素成一个字符串(new);
     // <li>repeat - 重复多次组合成新的字符串(new);
     // <li>is... - 检查字符串中包含的字符的类型(new);
     // <li>defaultString - 返回默认的字符串，将null变为空白字符(new);
     // <li>htmlEncode - 在字符串中替换html标记所用的六个特殊字符：& \ " ' &lt; &gt;
     // <li>return2Br - 将字符串中的换行符"\n"转换为html换行标记"&lt;br&gt;"
     // <li>blank2NBSP - 将字符串中的空格" "转换为html的空格标记"&amp;nbsp;"
     // <li>urlEncodeNPath - 能解决一些路径中的文件名包含中文的问题  
     // <li>cutShort - 使用省略号(...)将过长的字符串截短
     // <li>htmlCutShort - 使用省略号(...)将过长的字符串截短并转码html中的特殊字符
     // <li>escapeHtml - 使用HTML entities中出现的字符，将字符串中的需要转换的字符全部逃逸
     // <li>Html - escapeHtml的反操作
     // <li>escapeXml - 使用XML entities中出现四个常用的字符，将字符串中的需要转换的字符全部逃逸
     // <li>Xml - escapeXml的反操作
     // <li>toToken - 除去字符串中的所有逗号分割符
     // <li>toToken - 除去字符串中的所有指定的分割符
     // <li>leftTrim - 将字符串左边的所有空格去掉。
     // <li>rightTrim - 将字符串右边的所有空格去掉。
     // <li>leftTrim - 将字符串左边的所有空格去掉。
     // <li>reverseString - 反转字符串。
     // </ul>
  ///
public class StringUtil {
 
 public StringUtil() {}
 
 /////
  // 转换中文字符集，由"GBK"码转换为"ISO8859_1"码。
  //
  // param sSource 需要转换的字符串。
  // return 返回转换后的字符串。
  ///
 public static String unicodeToGB(String sSource) throws EncodingException {
  try {
   String S_Desc = "";
   if (sSource != null && !sSource.trim().equals("")) {
    byte b_Proc [] = sSource.getBytes("GBK");
    S_Desc = new String(b_Proc, "ISO8859_1");
   }
   return S_Desc;
  }catch(UnsupportedEncodingException e) {
   EncodingException encode = new EncodingException(e.getMessage());
   throw encode;
  }
 }

 /////
  // 转换中文字符集，由"ISO8859_1"码转换为"GBK"码。
  //
  // param sSource 需要转换的字符串。
  // return 返回转换后的字符串。
  ///
 public static String GBToUnicode(String sSource) throws EncodingException {
  try {
   String S_Desc = "";
   if (sSource != null && !sSource.trim().equals("")) {
    byte b_Proc [] = sSource.getBytes("ISO8859_1");
    S_Desc = new String(b_Proc, "GBK");
   }
   return S_Desc;
  }catch(UnsupportedEncodingException e) {
   EncodingException encode = new EncodingException(e.getMessage());
   throw encode;
  }
 }

    /////
     // 在字符串中进行查找替换，给出替换后的字符串.
     //
     // 如果是null 传入将返回不替换而返回原字符串.
     // 例子：
     // <pre>
     // StringUtil.replace(null, //, //)        = null
     // StringUtil.replace("", //, //)          = ""
     // StringUtil.replace("aba", null, null) = "aba"
     // StringUtil.replace("aba", null, null) = "aba"
     // StringUtil.replace("aba", "a", null)  = "aba"
     // StringUtil.replace("aba", "a", "")    = "aba"
     // StringUtil.replace("aba", "a", "z")   = "zbz"
     // </pre>
     //
     // param text  text to search and replace in, may be null
     // param repl  the String to search for, may be null
     // param with  the String to replace with, may be null
     // return the text with any replacements processed,
     //  <code>null</code> if null String input
     ///
    public static String replace(String text, String repl, String with) {
        return StringUtils.replace(text,repl,with);
    }

 /////
  // 在字符串中替换html标记所用的六个特殊字符：& \ " ' &lt; &gt;
  //
  // param sSource 要替换的字符串。
  // return 返回替换后的字符串。
  ///
 public static String htmlEncode(String sSource) {
  String sTemp = sSource;
  sTemp = replace(sTemp, "&", "&amp;");
  //sTemp = replace(sTemp, """, "&quot;");
  sTemp = replace(sTemp, "'", "&#039;");
  sTemp = replace(sTemp, "<", "&lt;");
  sTemp = replace(sTemp, ">", "&gt;");
  return sTemp;
 }
 
 /////
  // 将字符串中的换行符"\n"转换为html换行标记"&lt;br&gt;"
  //
  // param sSource 要替换的字符串。
  // return 返回替换后的字符串。
  ///
 public static String return2Br(String sSource) {
  return replace(sSource, "\n", "<br>");
 }
 
 /////
  // 将字符串中的空格" "转换为html的空格标记"&amp;nbsp;"
  //
  // param sSource 要替换的字符串。
  // return 返回替换后的字符串。
  ///
 public static String blank2NBSP(String sSource) {
  return replace(sSource, " ", "&nbsp;");
 }
 
    /////
     // 使用给定的分隔符将传入的字符串分割成字符串数组.
     //
     // 分隔符不包括在返回的字符串数组中.相邻的分隔符作为一个分隔符处理
     //
     // A <code>null</code> input String returns <code>null</code>.
     // 例子：
     // <pre>
     // StringUtil.split(null, //)         = null
     // StringUtil.split("", //)           = []
     // StringUtil.split("a.b.c", '.')    = ["a", "b", "c"]
     // StringUtil.split("a..b.c", '.')   = ["a", "b", "c"]
     // StringUtil.split("a:b:c", '.')    = ["a:b:c"]
     // StringUtil.split("a\tb\nc", null) = ["a", "b", "c"]
     // StringUtil.split("a b c", ' ')    = ["a", "b", "c"]
     // </pre>
     //
     // param str  the String to parse, may be null
     // param separatorChar  the character used as the delimiter,
     //  <code>null</code> splits on whitespace
     // return an array of parsed Strings, <code>null</code> if null String input
     ///
    public static String[] split(String str, char separatorChar) {
        return StringUtils.split(str,separatorChar);
    }

    /////
     // 使用给定的分隔符将传入的字符串分割成字符串数组.
     // 分隔符不包括在返回的字符串数组中.相邻的分隔符作为一个分隔符处理
     //
     // <p>A <code>null</code> input String returns <code>null</code>.
     // A <code>null</code> separatorChars splits on whitespace.</p>
     // 例子：
     // <pre>
     // StringUtil.split(null, //)         = null
     // StringUtil.split("", //)           = []
     // StringUtil.split("abc def", null) = ["abc", "def"]
     // StringUtil.split("abc def", " ")  = ["abc", "def"]
     // StringUtil.split("abc  def", " ") = ["abc", "def"]
     // StringUtil.split("ab:cd:ef", ":") = ["ab", "cd", "ef"]
     // </pre>
     //
     // param str  the String to parse, may be null
     // param separatorChars  the characters used as the delimiters,
     //  <code>null</code> splits on whitespace
     // return an array of parsed Strings, <code>null</code> if null String input
     ///
    public static String[] split(String str, String separatorChars) {
        return StringUtils.split(str, separatorChars);
    }
  
    /////
     // 把一个传入的字符串重复 <code>repeat</code> 次,然后组成一个新的字符串.
     // 例子：
     // <pre>
     // StringUtil.repeat(null, 2) = null
     // StringUtil.repeat("", 0)   = ""
     // StringUtil.repeat("", 2)   = ""
     // StringUtil.repeat("a", 3)  = "aaa"
     // StringUtil.repeat("ab", 2) = "abab"
     // StringUtil.repeat("a", -2) = ""
     // </pre>
     //
     // param str  the String to repeat, may be null
     // param repeat  number of times to repeat str, negative treated as zero
     // return a new String consisting of the original String repeated,
     //  <code>null</code> if null String input
     ///
    public static String repeat(String str, int repeat) {
        return StringUtils.repeat(str,repeat);
    }

    /////
     // <p>将提供的数组中的元素组合成一个字符串.</p>
     //
     // <p>No separator is added to the joined String.
     // Null objects or empty strings within the array are represented by
     // empty strings.</p>
     // 例子：
     // <pre>
     // StringUtil.join(null)            = null
     // StringUtil.join([])              = ""
     // StringUtil.join([null])          = ""
     // StringUtil.join(["a", "b", "c"]) = "abc"
     // StringUtil.join([null, "", "a"]) = "a"
     // </pre>
     //
     // param array  the array of values to join together, may be null
     // return the joined String, <code>null</code> if null array input
     ///
    public static String join(Object[] array) {
        return StringUtils.join(array);
    }

    /////
     // <p>将提供的数组中的元素组合成一个字符串.</p>
     //
     // <p>No delimiter is added before or after the list.
     // Null objects or empty strings within the array are represented by
     // empty strings.</p>
     // 例子:
     // <pre>
     // StringUtil.join(null, //)               = null
     // StringUtil.join([], //)                 = ""
     // StringUtil.join([null], //)             = ""
     // StringUtil.join(["a", "b", "c"], ';')  = "a;b;c"
     // StringUtil.join(["a", "b", "c"], null) = "abc"
     // StringUtil.join([null, "", "a"], ';')  = ";;a"
     // </pre>
     //
     // param array  the array of values to join together, may be null
     // param separator  the separator character to use
     // return the joined String, <code>null</code> if null array input
     ///
     public static String join(Object[] array, char separator) {
         return StringUtils.join(array,separator);
     }

    /////
     // <p>将提供的数组中的元素组合成一个字符串.</p>
     //
     // <p>No delimiter is added before or after the list.
     // A <code>null</code> separator is the same as an empty String ("").
     // Null objects or empty strings within the array are represented by
     // empty strings.</p>
     // 例子：
     // <pre>
     // StringUtil.join(null, //)                = null
     // StringUtil.join([], //)                  = ""
     // StringUtil.join([null], //)              = ""
     // StringUtil.join(["a", "b", "c"], "--")  = "a--b--c"
     // StringUtil.join(["a", "b", "c"], null)  = "abc"
     // StringUtil.join(["a", "b", "c"], "")    = "abc"
     // StringUtil.join([null, "", "a"], ',')   = ",,a"
     // </pre>
     //
     // param array  the array of values to join together, may be null
     // param separator  the separator character to use, null treated as ""
     // return the joined String, <code>null</code> if null array input
     ///
     public static String join(Object[] array, String separator) {
         return StringUtils.join(array,separator);
     }

    /////
     // <p>将提供的 <code>Iterator</code>中的元素组合成一个字符串.</p>
     //
     // <p>No delimiter is added before or after the list. Null objects or empty
     // strings within the iteration are represented by empty strings.</p>
     //
     // <p>See the examples here: {link #join(Object[],char)}. </p>
     //
     // param iterator  the <code>Iterator</code> of values to join together, may be null
     // param separator  the separator character to use
     // return the joined String, <code>null</code> if null iterator input
     ///
     public static String join(Iterator<String> iterator, char separator) {
         return StringUtils.join(iterator,separator);
     }

    /////
     // <p>将提供的 <code>Iterator</code>中的元素组合成一个字符串.</p>
     //
     // <p>No delimiter is added before or after the list.
     // A <code>null</code> separator is the same as an empty String ("").</p>
     //
     // <p>See the examples here: {link #join(Object[],String)}. </p>
     //
     // param iterator  the <code>Iterator</code> of values to join together, may be null
     // param separator  the separator character to use, null treated as ""
     // return the joined String, <code>null</code> if null iterator input
     ///
     public static String join(Iterator<String> iterator, String separator) {
         return StringUtils.join(iterator,separator);
     }
   
    /////
     // <p>检查是否字符串仅包含字母.</p>
     //
     // <p><code>null</code> will return <code>false</code>.
     // An empty String ("") will return <code>true</code>.</p>
     // 例子：
     // <pre>
     // StringUtil.isAlpha(null)   = false
     // StringUtil.isAlpha("")     = true
     // StringUtil.isAlpha("  ")   = false
     // StringUtil.isAlpha("abc")  = true
     // StringUtil.isAlpha("ab2c") = false
     // StringUtil.isAlpha("ab-c") = false
     // </pre>
     //
     // param str  the String to check, may be null
     // return <code>true</code> if only contains letters, and is non-null
     ///
     public static boolean isAlpha(String str) {
         return StringUtils.isAlpha(str);
     }

    /////
     // <p>检查是否字符串仅包含字母或数字.</p>
     //
     // <p><code>null</code> will return <code>false</code>.
     // An empty String ("") will return <code>true</code>.</p>
     // 例子：
     // <pre>
     // StringUtil.isAlphanumeric(null)   = false
     // StringUtil.isAlphanumeric("")     = true
     // StringUtil.isAlphanumeric("  ")   = false
     // StringUtil.isAlphanumeric("abc")  = true
     // StringUtil.isAlphanumeric("ab c") = false
     // StringUtil.isAlphanumeric("ab2c") = true
     // StringUtil.isAlphanumeric("ab-c") = false
     // </pre>
     //
     // param str  the String to check, may be null
     // return <code>true</code> if only contains letters or digits,
     //  and is non-null
     ///
     public static boolean isAlphanumeric(String str) {
         return StringUtils.isAlphanumeric(str);
     }

     /////
     // <p>检查是否字符串仅包含数字，浮点数不是一个数字，所以返回false.</p>
     //
     // <p><code>null</code> will return <code>false</code>.
     // An empty String ("") will return <code>true</code>.</p>
     // 例子：
     // <pre>
     // StringUtil.isNumeric(null)   = false
     // StringUtil.isNumeric("")     = true
     // StringUtil.isNumeric("  ")   = false
     // StringUtil.isNumeric("123")  = true
     // StringUtil.isNumeric("12 3") = false
     // StringUtil.isNumeric("ab2c") = false
     // StringUtil.isNumeric("12-3") = false
     // StringUtil.isNumeric("12.3") = false
     // </pre>
     //
     // param str  the String to check, may be null
     // return <code>true</code> if only contains digits, and is non-null
     ///
     public static boolean isNumeric(String str) {
         return StringUtils.isNumeric(str);
     }

    /////
     // <p>检查是否字符串仅包含空白.</p>
     //
     // <p><code>null</code> will return <code>false</code>.
     // An empty String ("") will return <code>true</code>.</p>
     // 例子：
     // <pre>
     // StringUtil.isWhitespace(null)   = false
     // StringUtil.isWhitespace("")     = true
     // StringUtil.isWhitespace("  ")   = true
     // StringUtil.isWhitespace("abc")  = false
     // StringUtil.isWhitespace("ab2c") = false
     // StringUtil.isWhitespace("ab-c") = false
     // </pre>
     //
     // param str  the String to check, may be null
     // return <code>true</code> if only contains whitespace, and is non-null
     ///
     public static boolean isWhitespace(String str) {
         return StringUtils.isWhitespace(str);
     }

    /////
     // <p>返回原字符串，当传入的字符串是<code>null</code>时, 返回空字符串("").</p>
     // 例子:
     // <pre>
     // StringUtil.defaultString(null)  = ""
     // StringUtil.defaultString("")    = ""
     // StringUtil.defaultString("bat") = "bat"
     // </pre>
     //
     // param str  the String to check, may be null
     // return the passed in String, or the empty String if it
     //  was <code>null</code>
     ///
     public static String defaultString(String str) {
         return StringUtils.defaultString(str);
     }

    /////
     // <p>返回原字符串，当传入的字符串是<code>null</code>或者为空字符串时, 返回输入的默认值.</p>
     // 例子：
     // <pre>
     // StringUtil.defaultString(null, "null")  = "null"
     // StringUtil.defaultString("", "null")    = "null"
     // StringUtil.defaultString("bat", "null") = "bat"
     // </pre>
     //
     // param str  the String to check, may be null
     // param defaultStr  the default String to return
     //  if the input is <code>null</code>, may be null
     // return the passed in String, or the default if it was <code>null</code> or empty string.
     ///
      public static String defaultString(String str, String defaultStr) {
          if(str==null || str.equals("")){
             return defaultStr;
          }else {
             return str;
         }
     }
 

    /////
  //将路径中的文件名单独取出并用URLEncode编码然后组成新的路径,能解决一些路径中的文件名包含中文的问题
  //
  //param path  路径字符串
  //return 返回编码后的路径字符串，如果参数为空或者长度为0返回0长度字符串
  ///
  public static String urlEncodeNPath(String path) {
    String fileName = "", prefix = "", suffix = "";
   if (path == null || path.trim().length() == 0)
   return "";
   int intDot   = path.lastIndexOf(".");
   int intSlash = path.lastIndexOf("/");
   //取最靠后的正斜杠或者反斜杠
   intSlash = (intSlash >= path.lastIndexOf("\\")) ? intSlash : path.lastIndexOf("\\");
   if (intSlash == -1)
    intSlash = 0;
   else
   intSlash = intSlash + 1;

   if (intDot > intSlash)
   suffix = path.substring(intDot, path.length());

   //CisConstants.printMsg("suffix: " + suffix);
   prefix = path.substring(0, intSlash);
   //CisConstants.printMsg("prefix: " + prefix);
   if (intDot > intSlash)
    fileName = path.substring(intSlash, intDot);
   else
   fileName = path.substring(intSlash, path.length());
   //CisConstants.printMsg("filename: " + fileName);
         String temp="";
         try {
            temp=prefix + URLEncoder.encode(fileName,"GBK") + suffix;
         } catch (Exception e) {}
     
         return temp;
 }
 

     /////
      // 使用省略号(...)将过长的字符串截短。
      //
      // 使用注意:
      // 如果str的长度短于maxWidth个字符个数，返回str本身.
      // 如果str的长度长于maxWidth个字符个数，将被截短到(substring(str, 0, max-3) + "...")
      // 如果maxWidth小于4, 异常IllegalArgumentException将会抛出.
      // 返回的字符串的长度永远不会长于maxWidth
      //
      // <pre>
      // StringUtil.cutShort(null, //)      = null
      // StringUtil.cutShort("", 4)        = ""
      // StringUtil.cutShort("abcdefg", 6) = "abc..."
      // StringUtil.cutShort("abcdefg", 7) = "abcdefg"
      // StringUtil.cutShort("abcdefg", 8) = "abcdefg"
      // StringUtil.cutShort("abcdefg", 4) = "a..."
      // StringUtil.cutShort("abcdefg", 3) = IllegalArgumentException
      // </pre>
      //
      // param str  要被截短的字符串,可以为 null
      // param maxWidth  截短后字符串的最大长度, 但必须大于等于 4
      // return 截短后的字符串, 如果传入null字符串则返回<code>null</code>
      // throws IllegalArgumentException 如果长度小于 4
      ///
       public static String cutShort(String str, int maxWidth)throws IllegalArgumentException {
           try{ 
              return StringUtils.abbreviate(str, 0, maxWidth);
           
           }catch(IllegalArgumentException e) {
        	   IllegalArgumentException ille = new IllegalArgumentException(e.getMessage());
           
            throw ille;
           }
       }
     /////
      // 使用省略号(...)将过长的字符串截短并转码html中的特殊字符
      // param str 要被截短的字符串,可以为 null
      // param maxWidth 截短后字符串的最大长度, 但必须大于等于 4
      // return 截短后并转码了html中的特殊字符的字符串, 如果传入null字符串则返回<code>null</code>
      // throws IllegalArgumentException 如果长度小于 4
      ///
       public static String htmlCutShort(String str, int maxWidth)throws IllegalArgumentException{
           try {
               return htmlEncode(cutShort(str, maxWidth));
           }catch(IllegalArgumentException e) {
        	   IllegalArgumentException ille = new IllegalArgumentException(e.getMessage());
            throw ille;
           }
       }

        /////
         // 使用HTML entities中出现的字符，将字符串中的需要转换的字符全部逃逸.
         // 例如: <tt>"bread" & "butter"</tt> => <tt>&amp;quot;bread&amp;quot; &amp;amp; &amp;quot;butter&amp;quot;</tt>.
         //
         // <p>支持所有的已知的 HTML 4.0 entities.</p>
         //
         // param str  需要进行HTML逃逸字符出来的字符串，可能为 null
         // return 返回已经进行逃逸处理后的字符串, 如果输入参数为null字符串则返回null
         //
         // see #Html(String)
         // see </br><a href="http://hotwired.lycos.com/webmonkey/reference/special_characters/">ISO Entities</a>
         // see </br><a href="http://www.w3.org/TR/REC-html32#latin1">HTML 3.2 Character Entities for ISO Latin-1</a>
         // see </br><a href="http://www.w3.org/TR/REC-html40/sgml/entities.html">HTML 4.0 Character entity references</a>
         // see </br><a href="http://www.w3.org/TR/html401/charset.html#h-5.3">HTML 4.01 Character References</a>
         // see </br><a href="http://www.w3.org/TR/html401/charset.html#code-position">HTML 4.01 Code positions</a>
         /////
        public static String escapeHtml(String str) {
            return StringEscapeUtils.escapeHtml(str);
        }

        /////
         // 反逃逸操作(解码).
         //例如, 字符串 "&amp;lt;Fran&amp;ccedil;ais&amp;gt;"
         // 操作后将返回 "&lt;Fran&ccedil;ais&gt;"
         //如果entity不被识别,将逐字返回到结果字符串. 例："&amp;gt;&amp;zzzz;x" 将变为
         // "&gt;&amp;zzzz;x".
         //
         // param str  将被反逃逸的字符串, 可能为 null
         // return 返回已经进行反逃逸处理后的字符串, 如果输入参数为null字符串则返回null
         // see #escapeHtml(String)
         /////
//        public static String Html(String str) {
//            return StringEscapeUtils.Html(str);
//        }

        /////
         // 使用XML entities中出现四个常用的字符，将字符串中的需要转换的字符全部逃逸.
         //
         // <p>例如: <tt>"bread" & "butter"</tt> =>
         // <tt>&amp;quot;bread&amp;quot; &amp;amp; &amp;quot;butter&amp;quot;</tt>.
         // </p>
         //
         // <p>仅支持逃逸这四个基本的 XML entities (gt, lt, quot, amp).
         // 不支持 DTDs 或扩展的实体(entities).</p>
         //
         // param str  需要进行逃逸操作的字符串, 可能为 null
         // return 返回已经进行逃逸处理后的字符串, 如果输入参数为null字符串则返回null
         // see #Xml(java.lang.String)
         /////
        public static String escapeXml(String str) {
            return StringEscapeUtils.escapeXml(str);
        }

        /////
         // 将字符串中包含 XML entity 的部分转换成实际的相应的Unicode字符.     
         // 仅支持逃逸这四个基本的 XML entities (gt, lt, quot, amp).
         // 不支持 DTDs 或 扩展的实体(entities).
         //
         // param str 需要进行反逃逸操作的字符串, 可能为 null
         // return 返回已经进行反逃逸处理后的字符串, 如果输入参数为null字符串则返回null
         // see #escapeXml(String)
         /////
//        public static String Xml(String str) {
//            return StringEscapeUtils.Xml(str);
//        }

    /////
     // 把字符串中html标记所用的六个特殊字符替换回正常字符：&amp;quot;（&quot;） &amp;#039;（&#039;）
     // &amp;lt;（&lt;） &amp;gt;（&gt;） &amp;amp;（&amp;） 。
     //
     // param sSource 要替换的字符串。
     // return 返回替换后的字符串。
     ///
    public static String htmlDecode(String sSource) {
        String sTemp = sSource;
        //sTemp = replace(sTemp, "&quot;", """);
        sTemp = replace(sTemp, "&#039;", "'");
        sTemp = replace(sTemp, "&lt;", "<");
        sTemp = replace(sTemp, "&gt;", ">");
        sTemp = replace(sTemp, "&amp;", "&");
        return sTemp;
    }
  
    /////
     // 除去字符串中的所有逗号分割符（,）
     //
     // param s 需要处理的字符串
     // return 不含有逗号的字符串
     ///
    public static String toToken(String s) {
     if (s==null || s.trim().equals(""))
      return s;
        String newStr = new String("");
        StringTokenizer st = new StringTokenizer(s, ",");
        while (st.hasMoreTokens()) {
            newStr = newStr + st.nextToken();
        }
        return newStr;
    }
  
    /////
     // 除去字符串中的所有指定的分割符
     //
     // param s 需要处理的字符串
     // return 不含有逗号的字符串
     ///
    public static String toToken(String s,String val) {
     if (s==null || s.trim().equals(""))
      return s;
     if (val==null || val.equals(""))
      return s;
    
        String newStr = new String("");
        StringTokenizer st = new StringTokenizer(s, val);
        while (st.hasMoreTokens()) {
            newStr = newStr + st.nextToken();
        }
        return newStr;
    }

    /////
     // 将字符串左边的所有空格去掉。
     //
     // param str：将要被处理的字符串
     // return 左边没有空格的字符串
     ///
    public static String leftTrim(String str){
     if (str==null || str.equals("")) return str;
    
     StringBuilder sbf = new StringBuilder(str);    
     while (sbf.charAt(0)==' ') {
        sbf = sbf.deleteCharAt(0);       
     }
     return sbf.toString();
    }
  
    /////
     // 将字符串右边的所有空格去掉。
     // param str：将要被处理的字符串
     // return 右边没有空格的字符串
     ///
    public static String rightTrim(String str){
     if (str==null || str.equals("")) return str;
    
     StringBuilder sbf = new StringBuilder(str);    
     while (sbf.charAt(sbf.length()-1)==' ') {
        sbf = sbf.deleteCharAt(sbf.length()-1);       
     }
     return sbf.toString();
    }
  
    /////
     // 将字符串反转。
     // param str: 将要反转的字符串。
     // return 返回经过反转处理的字符串。
     ///
    public static String reverseString(String str){
     if (str==null || str.equals("")) return str;
    
     StringBuilder sbf = new StringBuilder(str);
     return sbf.reverse().toString();
    }
  
    /////
     // -----------------------------------
     // author 胡勇
     // -----------------------------------
     // 判断字符参数是否为空值
     ///
    public static boolean isNullValue(String value) {
     return value == null || value.trim().equals("");
    }
    /////
     // 解决split方法无法截取的字符串
     // author 马明铭
     // param str 需要截取的字符串
     // param val 按某个字符截取
     // return 返回截取后的长度
     ///
    public static int getLength(String str,String val){
     int length = 0;
     StringTokenizer st = new StringTokenizer(str,val);
     length = st.countTokens();
     return length;
    }
  
    /////
     // description 解决split方法无法截取的字符串
     // author 马明铭
     // param str 需要截取的字符串
     // param val 按某个字符截取
     // return 返回截取后的字符串数组
     ///
    public static String[] getString(String str,String val){
     StringTokenizer st = new StringTokenizer(str,val);
     String[] arrayContent = new String[st.countTokens()];
     int i = 0;
     while(st.hasMoreTokens()){
      arrayContent[i] = st.nextToken();
      i++;
     }
     return arrayContent;
    }
  
  
    /////
  // author 张涛
  // param o
  // return str
  // 功能 把传进的字符串进行替换。其中 '' 替换成 ‘’ "" 替换成 “” < 替换成 &lt; > 替换成 &gt;
  ///
 public static String adjustString(Object o) {
  String str = "";
  if (o != null)
   str = o.toString();
  else
   return str;
  boolean singleTag = false;
  boolean doubleTag = false;
  StringBuilder sb = new StringBuilder();
  for (int i = 0; i < str.length(); i++) {
   char c = str.charAt(i);
   if (c == '\'') {
    if (!singleTag)
     c = '‘';
    else
     c = '’';
    singleTag = !singleTag;
    sb.append(c);
    continue;
   }
   if (c == '"') {
    if (!doubleTag)
     c = '“';
    else
     c = '”';
    doubleTag = !doubleTag;
    sb.append(c);
    continue;
   }
   if (c == '<') {
    sb.append("&lt;");
    continue;
   }
   if (c == '>') {
    sb.append("&gt;");
    continue;
   }
   sb.append(c);
  }
  str = sb.toString();
  return str;
 }
 
 /////
  // 计算字符串长度，，半角字符两个算一个汉字
  // param text  要计算的字符串
  // return   长度
  ///
 public static int getStringLength(String text){
  int count = 0;
  int length = 0;
  if(text != null && text.length() > 0){
   char[] str = text.toCharArray();
   for(int i = 0; i < str.length; i++){
    if(32 < str[i] && str[i] < 127){
     count++;
    }
   }
   if(count % 2 == 0){
    length = text.length() - count + count / 2;
   }else
    length = text.length() - count + count / 2 + 1;
  }
  return length;
 }
 
 /////
  // 截取字符串方法，半角字符两个算一个汉字
  // param text   要截取的字符串
  // param length  要截取的长度
  // return    截取后的字符串，后面加三个点
  ///
 public static String cutString(String text,int length){
  int templngth = 0;
  int i = 0;
  int count = 0;
  if(length != 0 && text != null && text.length() > length){
   char[] str = text.toCharArray();
   for( ; i <= str.length; i++){
    boolean flag = false;
    if(32 < str[i] && str[i] < 127){
     flag = true;
     count++;
     if(count % 2 == 1)
      templngth++;
    }else
     templngth++;
    if(templngth == length){
     if(flag && i < str.length -1){
      if(32 < str[i + 1] && str[i + 1] < 127){
       i += 1;
      }
     }
     break;
    }
   }
   text = text.substring(0,++i) + "...";
  }
  return text;
 }
 
 /////
  // 将\n转换为'< b r >'，字符串未空时返回&nbsp；
  // param text
  // return
  ///
 public final static String convert2Br(String text){
  return "".equals(text) ? "&nbsp;" : StringUtil.return2Br(text);
 }
 
 /////
  // 比较两个版本字符串的大小，格式为：主版本号.次版本号.编译版本号
  // 有可能不包括编译版本号
  // param bigstr
  // param smallstr
  // return
  ///
 public final static boolean compare(String bigstr,String smallstr){
  /////
   // 检查是否包含编译版本号，如果没有默认补0
   ///
  if(bigstr.indexOf(".") == bigstr.lastIndexOf("."))
   bigstr = bigstr.concat(".0");

  if(smallstr.indexOf(".") == smallstr.lastIndexOf("."))
   smallstr = smallstr.concat(".0");
 
  /////
   // 第一步比较（主版本.次版本），转化为float
   // 第二步比较编译版本，转化为int
   ///
  float serverVersion = Float.valueOf(bigstr.substring(0,bigstr.lastIndexOf(".")));
  float clientVersion = Float.valueOf(smallstr.substring(0,smallstr.lastIndexOf(".")));
  if(serverVersion > clientVersion){//需要更新
   return true;
  }else if(serverVersion == clientVersion){//如果主版本.次版本相同，则比较编译版本号
   if(Integer.parseInt(bigstr.substring(bigstr.lastIndexOf(".") + 1)) > Integer.parseInt(smallstr.substring(smallstr.lastIndexOf(".") + 1))){
    return true;
   }
  }
  return false;
 }
 
 /////
  // 逗号分隔符
  ///
 public final static String SPLIT_COMMA = ",";
 
 //
  // 将数组转化为字符串，使用逗号分隔符连接
  // param array
  // return
  ///
 public final static String toString(int[] array){
  return toString(array,SPLIT_COMMA);
 }
 
 /////
  // 将数组转化为字符串，使用指定分隔符连接
  // param array
  // param splitStr
  // return
  ///
 public final static String toString(int[] array,String splitStr){
  if(array == null || array.length == 0)
   return "";
  int length = array.length;
  StringBuilder sb = new StringBuilder();
  for(int i = 0; i < length; i++){
   if(i < length -1)
    sb.append(array[i]).append(splitStr);
   else
    sb.append(array[i]);
  }
  return sb.toString();
 }
 public static void main(String[] args){
	 String a = "\\热力";
	 a = a.replaceAll("\\\\", "/");
	 System.out.println(a);
  //System.out.println(StringUtil.compare("2.0.5", "2.1"));
 }
}