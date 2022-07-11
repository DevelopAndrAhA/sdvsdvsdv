package service;


import neural_network.models.FullFaceFeatures;
import neural_network.models.ResponseModel;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;



@Service
@Transactional
public class MyServiceClass {

    @Autowired
    SessionFactory session;
    /*@Autowired
    @Qualifier(value = "mySession")
    LocalSessionFactoryBean sessionFactory;*/

    /*public void saveObjects() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@//172.22.31.55:1515/vabank", "fors", "ABANK_NEW");
        //Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@//127.0.0.1:1521/xe", "altyn", "111");
        //Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@//172.22.31.4:1521/somic", "fors", "somic");
        //Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@//172.22.31.51:1521/abank", "mobin", "mobin");
        Statement s = connection.createStatement();
        for (int i = 0; i < entity21And32BList.size(); i++) {
            Entity21And32B entity21And32B = entity21And32BList.get(i);
            *//*if(entity21And32B.getNetc().equals("NETC")){
                String sql = "insert into error_uni_196 (amount,acct,type_netc) VALUES (" + entity21And32B.getSumm() + ",'" + entity21And32B.getUni21() + "','"+entity21And32B.getNetc()+"')";
                System.out.println(sql);
                s.execute(sql);
            }*//*
            String sql = "insert into error_uni_196 (amount,acct,type_netc) VALUES (" + entity21And32B.getSumm() + ",'" + entity21And32B.getUni21() + "','"+entity21And32B.getNetc()+"')";
            System.out.println(sql);
            s.execute(sql);
        }
        s.close();
        connection.close();
    }*/

    public void save(FullFaceFeatures fullFaceFeatures){
        session.getCurrentSession().save(fullFaceFeatures);
    }


    public List getFullFeatures(){
        Criteria criteria =  session.getCurrentSession().createCriteria(FullFaceFeatures.class);
        List<FullFaceFeatures> list = criteria.list();
        if(list!=null && list.size()!=0){
            return list;
        }
        return new ArrayList<FullFaceFeatures>();
    }
    public List<ResponseModel> getFullFeatures(String inp_date){
        String sql = "select f.fullFaceFeatures_id, f.faceLabel, f.identifier, f.inp_date, f.photoName, f2.facefeatures_id, f2.lat, f2.lng ,f2.features " +
                "from FullFaceFeatures f inner join FaceFeatures f2 on f2.fullfacefeatures_id = f.fullFaceFeatures_id " +
                "where to_char(f.inp_date,'yyyy-mm-dd')='"+inp_date+"'  limit 500";
        SQLQuery sqlQuery = session.getCurrentSession().createSQLQuery(sql).addEntity(ResponseModel.class);
        List<ResponseModel> list = sqlQuery.list();
        return list;
    }

    public List<ResponseModel> getFullFeatures(double lat,double lng){
        double lat_temp = lat+0.001000;
        double lng_temp = lng+0.001000;

        String sql = "select f.fullFaceFeatures_id," +
                    " f.faceLabel," +
                    " f.identifier," +
                    " f.inp_date," +
                    " f.photoName," +
                    " f2.facefeatures_id," +
                    " f2.lat," +
                    " f2.lng" +
                    " from FullFaceFeatures f " +
                    "inner join FaceFeatures f2 " +
                    "on f2.fullfacefeatures_id = f.fullFaceFeatures_id " +
                    "where (f2.lat between "+lat+" and  " +lat_temp + " or (f2.lng between "+lng+" and "+lng_temp+")) "+
                     "and to_char(f.inp_date,'dd.mm.yyyy')=to_char(CURRENT_DATE,'dd.mm.yyyy')";
        System.out.println(sql);
        SQLQuery sqlQuery = session.getCurrentSession().createSQLQuery(sql).addEntity(ResponseModel.class);
        List<ResponseModel> list = sqlQuery.list();
        return list;
    }









}
