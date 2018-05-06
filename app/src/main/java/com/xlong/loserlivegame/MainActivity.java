package com.xlong.loserlivegame;

import android.app.Activity;
import android.app.Dialog;
import android.nfc.Tag;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xlong.loserlivegame.adapter.BuyListViewAdapter;
import com.xlong.loserlivegame.adapter.SaleListViewAdapter;
import com.xlong.loserlivegame.model.BuyModel;
import com.xlong.loserlivegame.model.Person;
import com.xlong.loserlivegame.model.SaleModel;
import com.xlong.loserlivegame.sqlitehelper.DBForGoods;
import com.xlong.loserlivegame.sqlitehelper.DBForPerson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static android.support.v7.app.AppCompatDelegate.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private final int DIALOG_NORMAL = 1001;
    private final int DIALOG_BUYORSELL = 1002;
    private final int DIALOG_SAVEORGET = 1003;
    private final int DIALOG_HOSPITAL = 1004;
    private final String DIGOUYOU_HEIGHER = "据传：市场上大肆宣传虚假广告，宣称地沟油就是香，每天都吃身体棒。地沟油成为紧俏商品。";
    private final String DIGOUYOU_LOWER = "据传：专家称地沟油含致癌物，大部分人都不敢再食用地沟油。地沟油价格萎靡。";
    private final String UPAN_HEIGHER = "据传：互联网网盘因涉黄被严查，多家网盘倒闭。扩容U盘出货量暴增，价格猛涨。";
    private final String UPAN_LOWER = "据传：部分互联网网盘未受清查影响，依然坚挺，吸引众多用户。扩容U盘销量下跌。";
    private final String JINGHUAYE_LOWER = "据传：美容养颜不如用黄瓜，用黄瓜不如找男朋友。精华液面膜价格一路下滑。";
    private final String JINGHUAYE_HEIGHER = "据传：美容养颜还是用精华液面膜最有效，喷在脸上，立即见效。";
    private final String IPHONE_HEIGHER = "据传：爱疯手机饥饿营销，有钱买不到。粉丝连夜排队购买。黄牛趁机抬高手机价格。";
    private final String IPHONE_LOWER = "据传：爱疯手机电池爆炸，吓坏用户。手机销量一路下滑，商家降价促销。";
    private final String LENOVO_HEIGHER = "据传：乱想电脑加大广告力度，品牌影响力上升。群众纷纷购买，各种机型价格上涨。";
    private final String LENOVO_LOWER = "据传：乱想电脑偷偷使用三流工厂代工，硬件品质恶劣被曝光。用户失去购买兴趣。";
    private final String DAMI_HEIGHER = "据传：生命在于运动，每天行走一万步，有益身体健康。大米手环成为必备。";
    private final String DAMI_LOWER = "据传：很多青年人发现，每天出去走一走，还是不如在家看片撸啊撸来的舒服。运动手环销量下滑。";
    private final String GOLD_LOWER = "据传：国际金融大鳄操控期货市场，黄金价格一路下行。黄金清仓甩卖。";
    private final String GOLD_HEIGHER = "据传：中国大妈听说黄金要涨价，狂买黄金首饰。黄金价格一路上涨。";
    private final String CAR_HEIGHER = "据传：车贩利用政策漏洞，二手汽车车牌可过户。二手车市场立即沸腾，用户争相购买。";
    private final String CAR_LOWER = "据传：因为污染，将推出更加严厉的限行政策。买车除了车震，基本毫无用途。车市顾客寥寥无几。";
    private final String MASK_HEIGHER = "据传：帝都雾霾橙色预警，市民疯狂抢购口罩。黑心棉口罩一时引领时尚，供不应求。";
    private final String MASK_LOWER = "据传：西北风强力吹散雾霾，口罩严重积压，低价抛售";
    private final String AV_HEIGHER = "据传：经常看AV光盘，能提高原型制作能力。PM疯狂抢购，AV光盘价格大涨";
    private final String AV_LOWER = " 据传：一些盗版AV光盘里面暗藏黄色小片，受到监管部门大力打击，几乎无人问津。";
    private final String STEAL = "地铁能把人挤怀孕！\n小偷偷了你10%现金";
    private final String BEATEN = "听说你卖的盗版AV？，\n小楼派人揍了你一顿。";
    private Toolbar toolbar;
    private Dialog dialog;
    private boolean saveorget = true;
    private RadioGroup rGroup;
    private RadioButton[] rButton;
    private TextView cashTv, walletTv, healthTv, renownTv, warehouseTv, spaceTv, setTv;
    private ListView saleListview, buyListview;
    private SaleListViewAdapter saleAdapter;
    private BuyListViewAdapter buyAdapter;
    private List<SaleModel> saleList;
    private List<BuyModel> buyList;
    private TextView testTv;
    private EditText inputNumEt;
    private List<String> newsData;
    private String currentNews;
    private List<SaleModel> currentList;
    private DBForGoods dbGoods;
    private DBForPerson dbPersons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDefaultNightMode(MODE_NIGHT_YES);
        dbGoods = new DBForGoods(this);
        dbPersons = new DBForPerson(this);
        initView();
        initData();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    //余额宝
                    case R.id.ab_money:
                        showDialog("", "", "你想存入", "元钱？", "", "是的", "算了", String.valueOf(Tools.getStringNum(cashTv.getText().toString())), DIALOG_SAVEORGET, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (saveorget) {
                                    //存钱
                                    if (inputNumEt.length() != 0) {
                                        if (Tools.getStringNum(cashTv.getText().toString()) - Integer.parseInt(inputNumEt.getText().toString()) < 0) {
                                            showDialog("银行经理", "你个穷逼，没那么多钱！赶紧滚！", "", "", "我艹！", "", "", "", DIALOG_NORMAL, new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    dialog.dismiss();
                                                }
                                            });
                                        } else {
                                            cashTv.setText("现金：" + String.valueOf(Tools.getStringNum(cashTv.getText().toString()) - Integer.parseInt(inputNumEt.getText().toString())));
                                            walletTv.setText("存款：" + String.valueOf(Tools.getStringNum(walletTv.getText().toString()) + Integer.parseInt(inputNumEt.getText().toString())));
                                            dialog.dismiss();
                                        }
                                    } else {
                                        showDialog("提示", "请输入金额", "", "", "好的！", "", "", "", DIALOG_NORMAL, new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                    }
                                } else {
                                    //取钱
                                    if (inputNumEt.length() != 0) {
                                        if (Tools.getStringNum(walletTv.getText().toString()) - Integer.parseInt(inputNumEt.getText().toString()) < 0) {
                                            showDialog("银行经理", "你个穷逼，没那么多钱！赶紧滚！", "", "", "我艹！", "", "", "", DIALOG_NORMAL, new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    dialog.dismiss();
                                                }
                                            });
                                        } else {
                                            cashTv.setText("现金：" + String.valueOf(Tools.getStringNum(cashTv.getText().toString()) + Integer.parseInt(inputNumEt.getText().toString())));
                                            walletTv.setText("存款：" + String.valueOf(Tools.getStringNum(walletTv.getText().toString()) - Integer.parseInt(inputNumEt.getText().toString())));
                                            dialog.dismiss();
                                        }
                                    } else {
                                        showDialog("提示", "请输入金额", "", "", "好的！", "", "", "", DIALOG_NORMAL, new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                    }
                                }
                            }
                        });
                        break;
                    //医院
                    case R.id.ab_hospital:
                        if (Tools.getStringNum(healthTv.getText().toString()) < 100) {
                            showDialog("主治大夫", "你这个病，很严重啊，没有" + ((100 - Tools.getStringNum(healthTv.getText().toString())) * 5000) +
                                    "元治不好！", "", "", "", "保命要紧", "太特么黑了", "", DIALOG_HOSPITAL, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (Tools.getStringNum(cashTv.getText().toString()) < ((100 - Tools.getStringNum(healthTv.getText().toString())) * 5000)) {
                                        showDialog("主治大夫", "没钱你看个毛病！", "", "", "哎...!", "", "", "", DIALOG_NORMAL, new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });
                                    }
                                }
                            });
                        } else {
                            showDialog("风骚护士", "身体不错嘛，要不要捐精啊？", "", "", "别，营养跟不上！", "", "", "", DIALOG_NORMAL, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                        }
                        break;
                    //仓库
                    case R.id.ab_room:
                        break;
                }
                return true;
            }
        });
        rGroup = (RadioGroup) findViewById(R.id.ab_radiogroup);
        rButton = new RadioButton[5];
        rButton[0] = (RadioButton) findViewById(R.id.ab_placeone);
        rButton[1] = (RadioButton) findViewById(R.id.ab_placetwo);
        rButton[2] = (RadioButton) findViewById(R.id.ab_placethree);
        rButton[3] = (RadioButton) findViewById(R.id.ab_placefour);
        rButton[4] = (RadioButton) findViewById(R.id.ab_placefive);
        cashTv = (TextView) findViewById(R.id.money);
        walletTv = (TextView) findViewById(R.id.wallet);
        healthTv = (TextView) findViewById(R.id.health);
        renownTv = (TextView) findViewById(R.id.renown);
        warehouseTv = (TextView) findViewById(R.id.warehouse);
        spaceTv = (TextView) findViewById(R.id.space);
        setTv = (TextView) findViewById(R.id.set);
        setTv.setOnClickListener(this);
        saleListview = (ListView) findViewById(R.id.sale_listview);
        buyListview = (ListView) findViewById(R.id.buy_listview);
        testTv = (TextView) findViewById(R.id.center_tv);
        testTv.setOnClickListener(this);
    }


    private void initData() {
        normalPrizeShow();
        rButton[0].performClick();
        boolean readgame = getIntent().getBooleanExtra("readgame", false);
        buyList = new ArrayList<>();
        if (readgame) {
            cashTv.setText("现金：" + String.valueOf(dbPersons.getPerson().getCash()));
            walletTv.setText("存款：" + String.valueOf(dbPersons.getPerson().getWallet()));
            healthTv.setText("健康：" + String.valueOf(dbPersons.getPerson().getHealth()));
            renownTv.setText("名声：" + String.valueOf(dbPersons.getPerson().getRenown()));
            buyList = dbGoods.getGoods();
            buyAdapter = new BuyListViewAdapter(MainActivity.this, buyList);
            buyListview.setAdapter(buyAdapter);
        }
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.ab_placeone:
                        rGBtnClick();
                        break;
                    case R.id.ab_placetwo:
                        rGBtnClick();
                        break;
                    case R.id.ab_placethree:
                        rGBtnClick();
                        break;
                    case R.id.ab_placefour:
                        rGBtnClick();
                        break;
                    case R.id.ab_placefive:
                        rGBtnClick();
                        break;
                }
            }
        });

        saleListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final TextView introTv = (TextView) view.findViewById(R.id.sale_listview_item_intro);
                final TextView moneyTv = (TextView) view.findViewById(R.id.sale_listview_item_price);
                showDialog("购买", "", "你想购买", "个" + introTv.getText(), "", "是的", "算了", String.valueOf(Tools.getStringNum(cashTv.getText().toString()) / Tools.getStringNum(moneyTv.getText().toString())), DIALOG_BUYORSELL, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (inputNumEt.getText().length() != 0) {
                            if (Integer.parseInt(moneyTv.getText().toString()) * Integer.parseInt(inputNumEt.getText().toString()) > Tools.getStringNum(cashTv.getText().toString())) {
                                showDialog("百货店主", "傻屌，买不起别摸！滚！", "", "", "我艹！", "", "", "", DIALOG_NORMAL, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                            } else {
                                buyList.add(new BuyModel(position, introTv.getText().toString(), Integer.parseInt(moneyTv.getText().toString()), Integer.parseInt(inputNumEt.getText().toString())));
                                buyAdapter = new BuyListViewAdapter(MainActivity.this, buyList);
                                buyListview.setAdapter(buyAdapter);
                                cashTv.setText("现金：" + String.valueOf(Tools.getStringNum(cashTv.getText().toString()) - Integer.parseInt(moneyTv.getText().toString()) * Integer.parseInt(inputNumEt.getText().toString())));
                                dialog.dismiss();
                            }
                        } else {
                            showDialog("百货店主", "傻屌，买不起别摸！滚！", "", "", "我艹！", "", "", "", DIALOG_NORMAL, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                        }
                    }
                });
            }
        });
        buyListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final TextView introTv = (TextView) view.findViewById(R.id.buy_listview_item_intro);
                final TextView numTv = (TextView) view.findViewById(R.id.buy_listview_item_num);
                showDialog("销售", "", "你想卖出", "个" + introTv.getText(), "", "是的", "算了", String.valueOf(Tools.getStringNum(numTv.getText().toString())), DIALOG_BUYORSELL, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean existGood = false;
                        int salePrize = 0;
                        if (inputNumEt.getText().length() != 0) {
                            for (int i = 0; i < currentList.size(); i++) {
                                if (currentList.get(i).getName().equals(buyList.get(position).getName())) {
                                    existGood = true;
                                    salePrize = currentList.get(i).getMoney();
                                    break;
                                } else {
                                    existGood = false;
                                }
                            }
                            if (existGood) {
                                if (Integer.parseInt(inputNumEt.getText().toString()) > buyList.get(position).getNum()) {
                                    showDialog("百货店主", "哼！想骗我，哪有那么容易！", "", "", "(⊙o⊙)…", "", "", "", DIALOG_NORMAL, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });
                                } else if (Integer.parseInt(inputNumEt.getText().toString()) < buyList.get(position).getNum()) {
                                    buyList.get(position).setNum(buyList.get(position).getNum() - Integer.parseInt(inputNumEt.getText().toString()));
                                    cashTv.setText("现金：" + (Integer.parseInt(inputNumEt.getText().toString()) * salePrize + Tools.getStringNum(cashTv.getText().toString())));
                                    buyAdapter.notifyDataSetChanged();
                                    dialog.dismiss();
                                } else {
                                    cashTv.setText("现金：" + (Integer.parseInt(inputNumEt.getText().toString()) * salePrize + Tools.getStringNum(cashTv.getText().toString())));
                                    buyList.remove(position);
                                    buyAdapter.notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            } else {
                                showDialog("提示", "该地区不收购该商品", "", "", "好的", "", "", "", DIALOG_NORMAL, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });
                            }
                        } else {
                            showDialog("提示", "请输入数量", "", "", "好的", "", "", "", DIALOG_NORMAL, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    /**
     * 显示正常价格
     */
    private void normalPrizeShow() {
        saleList = new ArrayList<>();
        saleList.add(new SaleModel("Q7汽车", createRandow(10000, 10000)));
        saleList.add(new SaleModel("乱想电脑", createRandow(6000, 1000)));
        saleList.add(new SaleModel("爱疯手机", createRandow(3000, 1000)));
        saleList.add(new SaleModel("18K金条", createRandow(1000, 1000)));
        saleList.add(new SaleModel("扩容U盘", createRandow(450, 100)));
        saleList.add(new SaleModel("大米手环", createRandow(200, 200)));
        saleList.add(new SaleModel("醇香地沟油", createRandow(110, 60)));
        saleList.add(new SaleModel("精华液面膜", createRandow(60, 70)));
        saleList.add(new SaleModel("黑心棉口罩", createRandow(20, 30)));
        saleList.add(new SaleModel("AV光盘", createRandow(8, 7)));

        newsData = new ArrayList<>();
        //将两个model类中金钱比较大小，进行排列
        Comparator comp = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                SaleModel s1 = (SaleModel) o1;
                SaleModel s2 = (SaleModel) o2;
                if (s1.getMoney() < s2.getMoney()) {
                    return 1;
                } else if (s1.getMoney() == s2.getMoney()) {
                    return 0;
                } else if (s1.getMoney() > s2.getMoney()) {
                    return -1;
                }
                return 0;
            }
        };

        List<Integer> positionList = new ArrayList<>();
        //不重复获取6位随机数
        int[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Random rand = new Random();
        for (int i = 10; i > 1; i--) {
            int index = rand.nextInt(i);
            int tmp = array[index];
            array[index] = array[i - 1];
            array[i - 1] = tmp;
        }
        int result = 0;
        for (int i = 0; i < 6; i++) {
            result = result * 10 + array[i];
            positionList.add(array[i]);
        }

        currentList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            currentList.add(saleList.get(positionList.get(i)));
        }

        //根据加载的数据加载新闻
        for (int i = 0; i < currentList.size(); i++) {
            switch (currentList.get(i).getName()) {
                case "Q7汽车":
                    newsData.add(CAR_HEIGHER);
                    newsData.add(CAR_LOWER);
                    break;
                case "乱想电脑":
                    newsData.add(LENOVO_HEIGHER);
                    newsData.add(LENOVO_LOWER);
                    break;
                case "爱疯手机":
                    newsData.add(IPHONE_HEIGHER);
                    newsData.add(IPHONE_LOWER);
                    break;
                case "18K金条":
                    newsData.add(GOLD_HEIGHER);
                    newsData.add(GOLD_LOWER);
                    break;
                case "扩容U盘":
                    newsData.add(UPAN_HEIGHER);
                    newsData.add(UPAN_LOWER);
                    break;
                case "大米手环":
                    newsData.add(DAMI_HEIGHER);
                    newsData.add(DAMI_LOWER);
                    break;
                case "醇香地沟油":
                    newsData.add(DIGOUYOU_HEIGHER);
                    newsData.add(DIGOUYOU_LOWER);
                    break;
                case "精华液面膜":
                    newsData.add(JINGHUAYE_HEIGHER);
                    newsData.add(JINGHUAYE_LOWER);
                    break;
                case "黑心棉口罩":
                    newsData.add(MASK_HEIGHER);
                    newsData.add(MASK_LOWER);
                    break;
                case "AV光盘":
                    newsData.add(AV_LOWER);
                    newsData.add(AV_HEIGHER);
                    break;
                default:
                    break;
            }
        }
        currentNews = newsData.get((int) (Math.random() * newsData.size()));

        switch (currentNews) {
            case CAR_HEIGHER:
                for (int i = 0; i < currentList.size(); i++) {
                    if (currentList.get(i).getName().equals("Q7汽车")) {
                        currentList.remove(i);
                        currentList.add(new SaleModel("Q7汽车", createRandow(30000, 20000)));
                    }
                }
                break;
            case CAR_LOWER:
                for (int i = 0; i < currentList.size(); i++) {
                    if (currentList.get(i).getName().equals("Q7汽车")) {
                        currentList.remove(i);
                        currentList.add(new SaleModel("Q7汽车", createRandow(10000, 10000)));
                    }
                }
                break;
            case LENOVO_HEIGHER:
                for (int i = 0; i < currentList.size(); i++) {
                    if (currentList.get(i).getName().equals("乱想电脑")) {
                        currentList.remove(i);
                        currentList.add(new SaleModel("乱想电脑", createRandow(10000, 5000)));
                    }
                }
                break;
            case LENOVO_LOWER:
                for (int i = 0; i < currentList.size(); i++) {
                    if (currentList.get(i).getName().equals("乱想电脑")) {
                        currentList.remove(i);
                        currentList.add(new SaleModel("乱想电脑", createRandow(2000, 1000)));
                    }
                }
                break;
            case IPHONE_HEIGHER:
                for (int i = 0; i < currentList.size(); i++) {
                    if (currentList.get(i).getName().equals("爱疯手机")) {
                        currentList.remove(i);
                        currentList.add(new SaleModel("爱疯手机", createRandow(5000, 3000)));
                    }
                }
                break;
            case IPHONE_LOWER:
                for (int i = 0; i < currentList.size(); i++) {
                    if (currentList.get(i).getName().equals("爱疯手机")) {
                        currentList.remove(i);
                        currentList.add(new SaleModel("爱疯手机", createRandow(1000, 1000)));
                    }
                }
                break;
            case GOLD_HEIGHER:
                for (int i = 0; i < currentList.size(); i++) {
                    if (currentList.get(i).getName().equals("18K金条")) {
                        currentList.remove(i);
                        currentList.add(new SaleModel("18K金条", createRandow(2000, 1000)));
                    }
                }
                break;
            case GOLD_LOWER:
                for (int i = 0; i < currentList.size(); i++) {
                    if (currentList.get(i).getName().equals("18K金条")) {
                        currentList.remove(i);
                        currentList.add(new SaleModel("18K金条", createRandow(500, 1000)));
                    }
                }
                break;
            case UPAN_HEIGHER:
                for (int i = 0; i < currentList.size(); i++) {
                    if (currentList.get(i).getName().equals("扩容U盘")) {
                        currentList.remove(i);
                        currentList.add(new SaleModel("扩容U盘", createRandow(650, 100)));
                    }
                }
                break;
            case UPAN_LOWER:
                for (int i = 0; i < currentList.size(); i++) {
                    if (currentList.get(i).getName().equals("扩容U盘")) {
                        currentList.remove(i);
                        currentList.add(new SaleModel("扩容U盘", createRandow(250, 100)));
                    }
                }
                break;
            case DAMI_HEIGHER:
                for (int i = 0; i < currentList.size(); i++) {
                    if (currentList.get(i).getName().equals("大米手环")) {
                        currentList.remove(i);
                        currentList.add(new SaleModel("大米手环", createRandow(500, 200)));
                    }
                }
                break;
            case DAMI_LOWER:
                for (int i = 0; i < currentList.size(); i++) {
                    if (currentList.get(i).getName().equals("大米手环")) {
                        currentList.remove(i);
                        currentList.add(new SaleModel("大米手环", createRandow(100, 200)));
                    }
                }
                break;
            case DIGOUYOU_HEIGHER:
                for (int i = 0; i < currentList.size(); i++) {
                    if (currentList.get(i).getName().equals("醇香地沟油")) {
                        currentList.remove(i);
                        currentList.add(new SaleModel("醇香地沟油", createRandow(200, 100)));
                    }
                }
                break;
            case DIGOUYOU_LOWER:
                for (int i = 0; i < currentList.size(); i++) {
                    if (currentList.get(i).getName().equals("醇香地沟油")) {
                        currentList.remove(i);
                        currentList.add(new SaleModel("醇香地沟油", createRandow(50, 60)));
                    }
                }
                break;
            case JINGHUAYE_HEIGHER:
                for (int i = 0; i < currentList.size(); i++) {
                    if (currentList.get(i).getName().equals("精华液面膜")) {
                        currentList.remove(i);
                        currentList.add(new SaleModel("精华液面膜", createRandow(100, 100)));
                    }
                }
                break;
            case JINGHUAYE_LOWER:
                for (int i = 0; i < currentList.size(); i++) {
                    if (currentList.get(i).getName().equals("精华液面膜")) {
                        currentList.remove(i);
                        currentList.add(new SaleModel("精华液面膜", createRandow(30, 70)));
                    }
                }
                break;
            case MASK_HEIGHER:
                for (int i = 0; i < currentList.size(); i++) {
                    if (currentList.get(i).getName().equals("黑心棉口罩")) {
                        currentList.remove(i);
                        currentList.add(new SaleModel("黑心棉口罩", createRandow(50, 30)));
                    }
                }
                break;
            case MASK_LOWER:
                for (int i = 0; i < currentList.size(); i++) {
                    if (currentList.get(i).getName().equals("黑心棉口罩")) {
                        currentList.remove(i);
                        currentList.add(new SaleModel("黑心棉口罩", createRandow(10, 20)));
                    }
                }
                break;
            case AV_HEIGHER:
                for (int i = 0; i < currentList.size(); i++) {
                    if (currentList.get(i).getName().equals("AV光盘")) {
                        currentList.remove(i);
                        currentList.add(new SaleModel("AV光盘", createRandow(20, 20)));
                    }
                }
            case AV_LOWER:
                for (int i = 0; i < currentList.size(); i++) {
                    if (currentList.get(i).getName().equals("AV光盘")) {
                        currentList.remove(i);
                        currentList.add(new SaleModel("AV光盘", createRandow(3, 3)));
                    }
                }
                break;
            default:

                break;
        }
//        Log.e(TAG + "点击", currentNews);
        Collections.sort(currentList, comp);
        saleAdapter = new SaleListViewAdapter(MainActivity.this, currentList);

        saleListview.setAdapter(saleAdapter);
    }

    /**
     * 设置dialog（开始游戏，保存进度）
     */
    private void showControlDialog() {
        final DBForPerson dbPerson = new DBForPerson(MainActivity.this);
        final DBForGoods dbGood = new DBForGoods(MainActivity.this);
        final Dialog condialog = new Dialog(this, R.style.Dialog);
        condialog.setContentView(R.layout.dialog_set);
        condialog.setCanceledOnTouchOutside(true);
        Button saveBtn = (Button) condialog.findViewById(R.id.save_game);
        Button startBtn = (Button) condialog.findViewById(R.id.start_game_again);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long addPersonId = 0;
                if (dbPerson.getPerson() != null) {
                    addPersonId = dbPerson.updatePerson(0, Tools.getStringNum(cashTv.getText().toString()),
                            Tools.getStringNum(walletTv.getText().toString()),
                            Tools.getStringNum(healthTv.getText().toString()),
                            Tools.getStringNum(renownTv.getText().toString()));
                    addBuyListToSql(dbGood);
                } else {
                    addPersonId = dbPerson.addPerson(new Person(0, Tools.getStringNum(cashTv.getText().toString()),
                            Tools.getStringNum(walletTv.getText().toString()),
                            Tools.getStringNum(healthTv.getText().toString()),
                            Tools.getStringNum(renownTv.getText().toString())));
                    addBuyListToSql(dbGood);
                }
                if (addPersonId != 1) {
                    Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                }

            }
        });
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("提示", "重新开始会清除已保存进度，确定重新开始？", "", "", "", "确定", "取消", "", DIALOG_HOSPITAL, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        normalPrizeShow();
                        rButton[0].performClick();
                        dbGood.deleteGoods();
                        dbPerson.deletePerson();
                        buyList.removeAll(buyList);
                        buyAdapter = new BuyListViewAdapter(MainActivity.this, buyList);
                        buyListview.setAdapter(buyAdapter);
                        dialog.dismiss();
                        condialog.dismiss();
                    }
                });
            }
        });
        condialog.show();
    }

    /**
     * @param noticeBtnText
     * @param intorduce
     * @param introBegin
     * @param introEnd
     * @param okBtnText
     * @param yesBtnText
     * @param noBtnText
     * @param inputNumEtText
     * @param changeUI
     * @param onClickListener
     */
    private void showDialog(String noticeBtnText, String intorduce, String introBegin, String introEnd, String okBtnText, String yesBtnText, String noBtnText,
                            String inputNumEtText, int changeUI, View.OnClickListener onClickListener) {
        if (dialog == null) {
            dialog = new Dialog(this, R.style.Dialog);
            dialog.setContentView(R.layout.dialog_saveorget_money);
            dialog.setCanceledOnTouchOutside(true);
            initDialog(noticeBtnText, intorduce, introBegin, introEnd, okBtnText, yesBtnText, noBtnText, inputNumEtText, changeUI, onClickListener);
        } else {
            initDialog(noticeBtnText, intorduce, introBegin, introEnd, okBtnText, yesBtnText, noBtnText, inputNumEtText, changeUI, onClickListener);
        }
        dialog.show();
    }

    /**
     * @param noticeBtnText
     * @param intorduce
     * @param introBegin
     * @param introEnd
     * @param okBtnText
     * @param yesBtnText
     * @param noBtnText
     * @param inputNumEtText
     * @param changeUI
     * @param onClickListener
     */
    private void initDialog(String noticeBtnText, String intorduce, String introBegin, String introEnd, String okBtnText, String yesBtnText, String noBtnText,
                            String inputNumEtText, int changeUI, View.OnClickListener onClickListener) {
        RadioButton saveRBtn = (RadioButton) dialog.findViewById(R.id.dialog_money_savemoney);
        RadioButton getRBtn = (RadioButton) dialog.findViewById(R.id.dialog_money_getmoney);
        RadioButton noticeRBtn = (RadioButton) dialog.findViewById(R.id.dialog_money_notice);
        TextView introduceTv = (TextView) dialog.findViewById(R.id.dialog_deal_introduce);
        Button okBtn = (Button) dialog.findViewById(R.id.dialog_deal_ok);
        LinearLayout yesornoLayout = (LinearLayout) dialog.findViewById(R.id.dialog_yesorno_layout);
        LinearLayout introSaveOrGetLayout = (LinearLayout) dialog.findViewById(R.id.dialog_money_layout);
        Button yesBtn = (Button) dialog.findViewById(R.id.dialog_money_yes);
        Button noBtn = (Button) dialog.findViewById(R.id.dialog_money_no);
        inputNumEt = (EditText) dialog.findViewById(R.id.dialog_money_inputmoney);
        final TextView introBeginTv = (TextView) dialog.findViewById(R.id.dialog_money_introduceBegin);
        final TextView introEndTv = (TextView) dialog.findViewById(R.id.dialog_money_introduceEnd);
        switch (changeUI) {
            case DIALOG_NORMAL:
                saveRBtn.setVisibility(View.GONE);
                getRBtn.setVisibility(View.GONE);
                noticeRBtn.setVisibility(View.VISIBLE);
                introduceTv.setVisibility(View.VISIBLE);
                okBtn.setVisibility(View.VISIBLE);
                yesornoLayout.setVisibility(View.GONE);
                introSaveOrGetLayout.setVisibility(View.GONE);
                introduceTv.setText(intorduce);
                okBtn.setText(okBtnText);
                noticeRBtn.setText(noticeBtnText);
                break;
            case DIALOG_BUYORSELL:
                saveRBtn.setVisibility(View.GONE);
                getRBtn.setVisibility(View.GONE);
                noticeRBtn.setVisibility(View.VISIBLE);
                introduceTv.setVisibility(View.GONE);
                introSaveOrGetLayout.setVisibility(View.VISIBLE);
                okBtn.setVisibility(View.GONE);
                yesornoLayout.setVisibility(View.VISIBLE);
                yesBtn.setText(yesBtnText);
                noBtn.setText(noBtnText);
                noticeRBtn.setText(noticeBtnText);
                inputNumEt.setText(inputNumEtText);
                break;
            case DIALOG_SAVEORGET:
                saveRBtn.setVisibility(View.VISIBLE);
                getRBtn.setVisibility(View.VISIBLE);
                saveRBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveorget = true;   //存钱
                        introBeginTv.setText("你想存入");
                        introEndTv.setText("元钱？");
                        inputNumEt.setText(String.valueOf(Tools.getStringNum(cashTv.getText().toString())));
                    }
                });
                saveRBtn.performClick();
                getRBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveorget = false;  //取钱
                        introBeginTv.setText("你想取出");
                        inputNumEt.setText(String.valueOf(Tools.getStringNum(walletTv.getText().toString())));
                        introEndTv.setText("元钱？");
                    }
                });
                noticeRBtn.setVisibility(View.GONE);
                introduceTv.setVisibility(View.GONE);
                introSaveOrGetLayout.setVisibility(View.VISIBLE);
                okBtn.setVisibility(View.GONE);
                yesornoLayout.setVisibility(View.VISIBLE);
                yesBtn.setText(yesBtnText);
                noBtn.setText(noBtnText);
                inputNumEt.setText(inputNumEtText);
                break;
            case DIALOG_HOSPITAL:
                saveRBtn.setVisibility(View.GONE);
                getRBtn.setVisibility(View.GONE);
                noticeRBtn.setVisibility(View.VISIBLE);
                introduceTv.setVisibility(View.VISIBLE);
                introSaveOrGetLayout.setVisibility(View.GONE);
                yesornoLayout.setVisibility(View.VISIBLE);
                okBtn.setVisibility(View.GONE);
                yesBtn.setText(yesBtnText);
                noBtn.setText(noBtnText);
                noticeRBtn.setText(noticeBtnText);
                introduceTv.setText(intorduce);
                break;
        }
        introBeginTv.setText(introBegin);
        introEndTv.setText(introEnd);
        yesBtn.setOnClickListener(onClickListener);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 显示被大或者被偷dialog
     *
     * @param introduce
     */
    private void beatDialogShow(String introduce) {
        switch (introduce) {
            case STEAL:
                cashTv.setText("现金：" + String.valueOf(Tools.getStringNum(cashTv.getText().toString()) - (int) (Tools.getStringNum(cashTv.getText().toString()) * 0.1)));
                break;
            case BEATEN:
                healthTv.setText("健康：" + String.valueOf(Tools.getStringNum(healthTv.getText().toString()) - 1));
                break;
        }
        final Dialog beatDialog = new Dialog(this, R.style.Dialog);
        beatDialog.setContentView(R.layout.dialog_beat);
        beatDialog.setCanceledOnTouchOutside(true);
        RadioButton noticeRBtn = (RadioButton) beatDialog.findViewById(R.id.beatdialog_money_notice);
        TextView introduceTv = (TextView) beatDialog.findViewById(R.id.beatdialog_deal_introduce);
        Button okBtn = (Button) beatDialog.findViewById(R.id.beatdialog_deal_ok);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beatDialog.dismiss();
            }
        });
        introduceTv.setText(introduce);
        beatDialog.show();
    }

    private void addBuyListToSql(DBForGoods dbGood) {
        if (buyList.size() != 0) {
            for (int i = 0; i < buyList.size(); i++) {
                for (int j = 0; j < dbGood.getGoodName().size(); j++) {
                    if (!buyList.get(i).getName().equals(dbGood.getGoodName().get(j))) {
                        dbGood.addGoods(buyList.get(i));
                    } else {
                        dbGood.updateGoods(buyList.get(i).getName(), buyList.get(i).getPrize(), buyList.get(i).getNum());
                    }
                }
            }
        }
    }

    /**
     * 生成随机数
     *
     * @param minNum
     * @param maxNum
     * @return
     */
    private int createRandow(int minNum, int maxNum) {
        return (int) (Math.random() * maxNum + minNum);
    }

    private void rGBtnClick() {
        String[] stealOrbeaten = new String[2];
        if (oddsDialogShow(0.5) != null) {
            normalPrizeShow();
            showDialog("小道新闻", currentNews, "", "", "确定", "", "", "", DIALOG_NORMAL, null);
        } else {
            normalPrizeShow();
        }
        stealOrbeaten[0] = STEAL;
        stealOrbeaten[1] = BEATEN;
        if (oddsDialogShow(0.2) != null) {
            beatDialogShow(stealOrbeaten[createRandow(0, 2)]);
        }
    }

    /**
     * 显示提示框的几率
     *
     * @param odds
     * @return
     */
    private String oddsDialogShow(double odds) {
        Arithmetic arithmetic = new Arithmetic();
        final HashMap<String, Double> map = new HashMap<>();
        map.put("1", odds);
        final List<HashMap<String, Double>> list = new ArrayList<>();
        list.add(map);
        if (arithmetic.pay(list) != null) {
        }
        return arithmetic.pay(list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set:
                showControlDialog();
                break;
        }
    }
}
