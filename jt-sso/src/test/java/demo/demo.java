/*package demo;

import java.awt.TextField;
import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;
*//**
 * 实现步骤：
 * 1.指定索引创建的文件夹
 * 2.定义配置文件conf
 * 3.创建索引输出对象
 * 4.创建文章document
 * 5.为文章添加内容
 * 6.为文章创建索引
 * throws IOException
 * 
 * @author bob
 *
 *//*
public class demo {
	*//**
	 * 实现步骤:
	 * 	1.指定索引创建的文件夹
	 *  2.定义配置文件conf
	 *  3.创建索引的输出对象
	 *  4.创建文章document
	 *  5.为文章添加内容
	 *  6.为文章创建索引
	 * @throws IOException 
	 *//*
	@Test
	public void test01() throws IOException{
		//指定索引的文件夹
		Directory directory = 
				FSDirectory.open(new File("./index"));
		
		//定义分词器      标准分词器 由外国人开发的
		//Analyzer analyzer = new StandardAnalyzer();
		Analyzer analyzer = new IKAnalyzer(); //中文分词器
		//创建输出的配置文件  4.10.2
		IndexWriterConfig conf = 
		new IndexWriterConfig(Version.LUCENE_4_10_2, analyzer);
		
		//定义索引输入对象
		IndexWriter writer = 
				new IndexWriter(directory, conf);
		
		//创建文章对象
		Document document = new Document();
		
		//TestField会通过分词器进行分词 StringField不会分词
		//定义商品的文章对象  
		document.add(new TextField("title", "黑鲨游戏手机 8GB+128GB 极夜黑 液冷更快 全网通4G 双卡双待", Store.YES));
		document.add(new TextField("sellPoint", "【购机送钢化膜＋游戏耳机】“吃鸡”利器骁龙845芯片！液冷散热！一键切换游戏模式，随时随地战力觉醒！",Store.YES));
		document.add(new LongField("price", 3499L, Store.YES));
		
		
		Document document1 = new Document();
		//TestField会通过分词器进行分词 StringField不会分词
		//定义商品的文章对象  
		document1.add(new TextField("title", "一加手机6 8GB+128GB 月牙白 全面屏双摄游戏手机 全网通4G 双卡双待", Store.YES));
		document1.add(new TextField("sellPoint", "白条3期免息！惊艳的玫瑰金配白，给你开箱胜过卡地亚的奢华质感，像Tiffny蓝一样的独特配色！更多精彩邀你来",Store.YES));
		document1.add(new LongField("price", 3599L, Store.YES));
		//为文章创建索引
		writer.addDocument(document);
		writer.addDocument(document1);
		//关闭流
		writer.close();
	}

	//通过倒排索引的方式实现文章的查询
		@Test
		public void search() throws IOException{
			//1.指定文章的位置
			Directory directory = FSDirectory.open(new File("./index"));
			//获取索引文件
			IndexSearcher indexSearcher = new IndexSearcher(IndexReader.open(directory));
			
			//创建查询的对象
			Query query = new TermQuery(new Term("title", "月牙"));
			
			//实现查询操作  查询前20条记录
			TopDocs docs = indexSearcher.search(query, 20);
			
			for (ScoreDoc doc : docs.scoreDocs) {
				//获取当前文章的下标
				int index = doc.doc;
				//获取文章的具体信息
				Document document = indexSearcher.doc(index);
				System.out.println("标题:"+document.get("title"));
				System.out.println("卖点:"+document.get("sellPoint"));
				System.out.println("价格:"+document.get("price"));
			}
			
			directory.close();
		}

}*/