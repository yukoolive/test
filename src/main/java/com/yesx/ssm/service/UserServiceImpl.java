package com.yesx.ssm.service;

import com.yesx.ssm.mapper.UserMapper;
import com.yesx.ssm.po.User;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;
    @Autowired
    private SolrClient client;

    @Override
    public User queryUserByLoginName(String loginName) {
        User user = userMapper.selectUser(loginName);
        return user;
    }

    @Override
    public boolean solrOptDemo() {
        try {
            //创建索引
            SolrInputDocument solrInputFields=new SolrInputDocument();
            solrInputFields.addField("id", 1);
            solrInputFields.addField("login_name", "yesx");
            solrInputFields.addField("password", "123456");
            solrInputFields.addField("role_id", 0);
            client.add(solrInputFields);
            client.commit();


            //基于实体类创建索引
            List<User> users=new ArrayList<User>();
            users.add(new User(2,"admin2","111111",0));
            users.add(new User(3,"admin3","222222",0));
            client.addBeans(users);
            client.commit();


            //查询第一种方式
            ModifiableSolrParams params =new ModifiableSolrParams();
            params.add("q","username:admin");
            params.add("ws","json");
            params.add("start","0");
            params.add("rows","10");
            QueryResponse queryResponse=client.query(params);
            System.out.println(queryResponse);


            //查询第二种方式
            int page = 1;
            int rows = 10;

            SolrQuery solrQuery = new SolrQuery(); // 构造搜索条件
            solrQuery.setQuery("username:7");// 搜索关键词
            // 设置分页
            solrQuery.setStart((Math.max(page, 1) - 1) * rows);
            solrQuery.setRows(rows);
            QueryResponse  docs = client.query(solrQuery);
            SolrDocumentList solrDocuments=docs.getResults();
            for(SolrDocument sd : solrDocuments){
                System.out.println(sd.get("id")+"#"+sd.get("username")+"#"+sd.get("password")+"#"+sd.get("vsername"));
            }


            //List<User> userList=docs.getBeans(User.class);//直接转实体(!这个B有问题，只有用上面的循环赋值比较靠谱)


            //删除索引
            client.deleteByQuery("id:2");
            client.commit();

            //通过版本号删除索引
            client.deleteById("123123");
            client.commit();

        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean setSolrUser(User user) {
        //基于实体类创建索引
        try {
            client.addBean(user);
            client.commit();
        }catch (Exception e) {
            System.out.println("---------------------solr创建索引："+user+"失败");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<User> selectSolrByLoginName(String loginName) {
        //查询第二种方式
        int page = 1;
        int rows = 10;

        SolrQuery solrQuery = new SolrQuery(); // 构造搜索条件
        solrQuery.setQuery("login_name:"+loginName);// 搜索关键词
        // 设置分页
        solrQuery.setStart((Math.max(page, 1) - 1) * rows);
        solrQuery.setRows(rows);
        QueryResponse  docs = null;
        try {
            docs = client.query(solrQuery);
        } catch (Exception e) {
            System.out.println("---------------------solr查询"+loginName+"失败");
            e.printStackTrace();
        }


        List<User> list = new ArrayList<User>();
        if(docs==null) return list;
        SolrDocumentList solrDocuments=docs.getResults();
        for(SolrDocument sd : solrDocuments){
            System.out.println(sd.get("id")+"#"+sd.get("login_name")+"#"+sd.get("password")+"#"+sd.get("role_id"));
            User user = new User((Integer) sd.get("id"),(String)sd.get("login_name"),(String)sd.get("password"),(Integer)sd.get("role_id"));
            list.add(user);
        }
        return list;
    }
}
